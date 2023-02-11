package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.config.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.LibraryRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseCodeDTO;
import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;
import com.steelrain.springboot.lilac.datamodel.view.MemberLoginDTO;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.datamodel.view.MemberProfileEditDTO;
import com.steelrain.springboot.lilac.datamodel.view.MemberRegDTO;
import com.steelrain.springboot.lilac.service.IMemberService;
import com.steelrain.springboot.lilac.service.KeywordCategoryCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final IMemberService m_memberService;
    private final KeywordCategoryCacheService m_keywordCategoryCacheService;


    @ModelAttribute("subjectCodes")
    public List<SubjectCodeDTO> getSubjectCodes(){
        return m_keywordCategoryCacheService.getSubjectCodeList();
    }

    @ModelAttribute("libRegionCodes")
    public List<LibraryRegionCodeDTO> getLibRegionCodes(){
        return m_keywordCategoryCacheService.getLibraryRegionCodeList();
    }

    @ModelAttribute("licenseCodes")
    public List<LicenseCodeDTO> getLicenseCodes(){
        return m_keywordCategoryCacheService.getLicenseCodeList();
    }

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

    @GetMapping("/profile")
    public String profileForm(HttpServletRequest servletRequest, Model model){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return "redirect:/";
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        MemberProfileEditDTO memberEditDTO = new MemberProfileEditDTO();
        memberEditDTO.setNickname(memberDTO.getNickname());
        memberEditDTO.setEmail(memberDTO.getEmail());
        memberEditDTO.setRegion(memberDTO.getRegion());
        memberEditDTO.setDtlRegion(memberDTO.getDtlRegion());
        memberEditDTO.setDescription(memberDTO.getDescription());
        model.addAttribute("memberInfo", memberEditDTO);

        return "member/profile";
    }

    @PostMapping("/profile")
    public RedirectView editMemberProfile(@Validated @ModelAttribute("memberInfo") MemberProfileEditDTO editDTO, BindingResult bindingResult,
                                          HttpServletRequest servletRequest, RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            log.error("회원정보 수정 에러 : {}", bindingResult);
            return new RedirectView("/member/profile");
        }
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return new RedirectView("/");
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        memberDTO.setNickname(editDTO.getNickname());
        memberDTO.setEmail(editDTO.getEmail());
        memberDTO.setDescription(editDTO.getDescription());
        m_memberService.updateMemberInfo(memberDTO);

        return new RedirectView("/member/profile");
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
            return "redirect:/member/registration";
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
        //특정 필드가 아닌 복합 룰 검증
        /*if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }*/

        if(bindingResult.hasErrors()){
            log.info("로그인 에러 : {}", bindingResult);
            return "redirect:/member/login";
        }
        MemberDTO memberDTO = m_memberService.loginMember(loginDTO.getEmail(), loginDTO.getPassword());
        HttpSession session = servletRequest.getSession();
        session.setAttribute(SESSION_KEY.LOGIN_MEMBER, memberDTO);
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
