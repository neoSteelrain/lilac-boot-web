package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.config.SessionKey;
import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.datamodel.form.LectureNoteAddDTO;
import com.steelrain.springboot.lilac.service.ILectureNoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

    private final ILectureNoteService m_lectureService;


    @GetMapping("/lecture-note")
    public String lectureNoteForm(HttpServletRequest servletRequest, Model model){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return "redirect:/";
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SessionKey.LOGIN_MEMBER);
        List<LectureNoteDTO> noteDTOList = m_lectureService.getLectureListByMember(memberDTO.getId());
        model.addAttribute("lectureNoteList", noteDTOList);
        model.addAttribute("noteAdd", new LectureNoteAddDTO());
        return "/lecture/lecture-note";
    }

    @PostMapping("/remove-note")
    public String removeLectureNote(@RequestParam("noteId") Long noteId){
        m_lectureService.removeLectureNote(noteId);

        return "redirect:/lecture/lecture-note";
    }

    /*
        모달창에서 빈 검증을 테스트해보기 위한 코드
        모달은 한번 뜨고 사라지는 폼이라서 그런지 빈 검증 에러 메시지는 못쓰지만, 검증 기능은 되는것 같다
     */
    @PostMapping("/add-note")
    public String addLectureNote(@Validated @ModelAttribute("noteDTO") LectureNoteAddDTO noteDTO, BindingResult bindingResult, HttpServletRequest request, Model model){
        if(bindingResult.hasErrors()){
            log.info("강의노트 생성 에러 : {}", bindingResult);
            
            HttpSession session = request.getSession();
            MemberDTO memberDTO = (MemberDTO) session.getAttribute(SessionKey.LOGIN_MEMBER);
            List<LectureNoteDTO> noteDTOList = m_lectureService.getLectureListByMember(memberDTO.getId());
            model.addAttribute("lectureNoteList", noteDTOList);
            model.addAttribute("noteAdd", new LectureNoteAddDTO());
            return "lecture/lecture-note";
        }
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SessionKey.LOGIN_MEMBER);

        m_lectureService.addLectureNote(memberDTO.getId(), noteDTO.getTitle(), noteDTO.getDescription());

        return "redirect:/lecture/lecture-note";
    }
}
