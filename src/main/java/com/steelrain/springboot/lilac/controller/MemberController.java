package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.config.SessionKey;
import com.steelrain.springboot.lilac.datamodel.LoginDTO;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.service.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final IMemberService m_memberService;


    @GetMapping("/login")
    public String loginForm(){
        return "member/login";
    }

    @GetMapping("/registration")
    public String registForm(){
        return "member/registration";
    }

    @GetMapping("/duplicated-email/{email}")
    public ResponseEntity<String> checkDuplicatedEmail(@PathVariable("email") String email){
        if(email.length() > 100){
            return new ResponseEntity<>("이메일주소는 100자 이하이어야 합니다.", HttpStatus.BAD_REQUEST);
        }
        return m_memberService.checkDuplicatedEmail(email) ?
                new ResponseEntity<>("중복된 이메일주소", HttpStatus.CONFLICT):
                new ResponseEntity<>("사용가능한 이메일주소", HttpStatus.OK);
    }

    @PostMapping("/registration")
    public String registMember(@ModelAttribute("member") MemberDTO memberDTO){

        boolean isRegisted = m_memberService.registerMember(memberDTO);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("LoginDTO")LoginDTO loginDTO,
                        HttpServletRequest servletRequest){
        MemberDTO memberDTO = m_memberService.loginMember(loginDTO.getEmail(), loginDTO.getPassword());

        HttpSession session = servletRequest.getSession();
        session.setAttribute(SessionKey.LOGIN_MEMBER, memberDTO);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest servletRequest){
        HttpSession session = servletRequest.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
