package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.config.SessionKey;
import com.steelrain.springboot.lilac.datamodel.form.MemberLoginDTO;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.datamodel.form.MemberRegDTO;
import com.steelrain.springboot.lilac.service.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public String loginForm(Model model){
        model.addAttribute("memberLogin", new MemberLoginDTO());
        return "member/login";
    }

    @GetMapping("/registration")
    public String registerForm(Model model){
        model.addAttribute("memberReg", new MemberRegDTO());
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
    public String registerMember(@Validated @ModelAttribute("memberReg") MemberRegDTO memberRegDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("회원가입 에러 : {}", bindingResult);
            return "member/registration";
        }

        MemberDTO memberDTO = MemberDTO.builder()
                .nickname(memberRegDTO.getNickname())
                .email(memberRegDTO.getEmail())
                .password(memberRegDTO.getPassword())
                .build();
        return  m_memberService.registerMember(memberDTO) ? "redirect:/member/registration" : "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("memberLogin") MemberLoginDTO loginDTO, BindingResult bindingResult,
                        HttpServletRequest servletRequest){
        if(bindingResult.hasErrors()){
            log.info("로그인 에러 : {}", bindingResult);
            return "member/login";
        }

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
