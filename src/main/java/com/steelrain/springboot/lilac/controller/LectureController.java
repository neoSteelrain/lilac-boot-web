package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.config.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.datamodel.form.LectureNoteAddDTO;
import com.steelrain.springboot.lilac.datamodel.form.PlayListAddModalDTO;
import com.steelrain.springboot.lilac.service.ILectureNoteService;
import com.steelrain.springboot.lilac.service.KeywordCategoryCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final KeywordCategoryCacheService m_keywordCategoryCacheService;


    @GetMapping("/lecture-note")
    public String lectureNoteForm(HttpServletRequest servletRequest, Model model){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return "redirect:/";
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(memberDTO == null){
            return "redirect:/";
        }

        List<LectureNoteDTO> noteDTOList = m_lectureService.getLectureListByMember(memberDTO.getId());
        model.addAttribute("lectureNoteList", noteDTOList);
        model.addAttribute("noteAdd", new LectureNoteAddDTO());
        model.addAttribute("memberId", memberDTO.getId());
        model.addAttribute("memberNickname", memberDTO.getNickname());
        model.addAttribute("memberEmail", memberDTO.getEmail());

        model.addAttribute("licenseCodes",m_keywordCategoryCacheService.getLicenseCodeList());
        model.addAttribute("subjectCodes",m_keywordCategoryCacheService.getSubjectCodeList());
        return "/lecture/lecture-note";
    }
    // TODO : 강의노트 정보를 한번에 넘겨줄 DTO가 필요하다
    @PostMapping("/remove-note")
    public String removeLectureNote(@RequestParam("noteId") Long noteId){
        m_lectureService.removeLectureNote(noteId);

        return "redirect:/lecture/lecture-note";
    }

    @GetMapping("modal-template")
    public String getLectureNoteListByMemberModal(@RequestParam("memberId") Long memberId, Long playListId, Model model, HttpServletRequest servletRequest){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return "redirect:/";
        }
        List<PlayListAddModalDTO> noteDTOList = m_lectureService.getLectureNoteListByMemberModal(memberId, playListId);
        model.addAttribute("lectureNoteList", noteDTOList);
        return "/lecture/lecture-note-modal-template";
    }

    /*
        모달창에서 빈 검증을 테스트해보기 위한 코드
        사용하지는 않는다.
     */
   /* @PostMapping("/add-note")
    public String addLectureNote(@Validated @ModelAttribute("noteDTO") LectureNoteAddDTO noteDTO, BindingResult bindingResult, HttpServletRequest request, Model model){
        if(bindingResult.hasErrors()){
            log.info("강의노트 생성 에러 : {}", bindingResult);
            
            HttpSession session = request.getSession();
            MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
            List<LectureNoteDTO> noteDTOList = m_lectureService.getLectureListByMember(memberDTO.getId());
            model.addAttribute("lectureNoteList", noteDTOList);
            model.addAttribute("noteAdd", new LectureNoteAddDTO());
            return "lecture/lecture-note";
        }
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);

        m_lectureService.addLectureNote(memberDTO.getId(), noteDTO.getTitle(), noteDTO.getDescription());

        return "redirect:/lecture/lecture-note";
    }*/
}
