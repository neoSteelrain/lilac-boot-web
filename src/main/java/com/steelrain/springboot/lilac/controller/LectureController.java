package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.common.ICacheService;
import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.datamodel.view.*;
import com.steelrain.springboot.lilac.service.ILectureNoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

    private final ILectureNoteService m_lectureService;
    private final ICacheService m_keywordCategoryCacheService;


    @GetMapping("/lecture-note")
    public String lectureNoteForm(HttpServletRequest servletRequest, Model model){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return "redirect:/";
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(memberDTO == null){
            return "redirect:/member/login";
        }

        List<LectureNoteDTO> noteDTOList = m_lectureService.getLectureListByMember(memberDTO.getId());
        model.addAttribute("lectureNoteList", noteDTOList);
        model.addAttribute("noteAdd", new LectureNoteAddDTO());
        model.addAttribute("memberInfo", memberDTO);

        // 강의노트 상세정보 설정
        LectureNoteDetailDTO noteDetailDTO = m_lectureService.getLectureNoteDetailInfoByMember(memberDTO.getId(), noteDTOList.get(0).getId());
        model.addAttribute("noteDetail", noteDetailDTO);

        // 강의노트를 업데이트하기 위해 필요한 키워드정보들
        model.addAttribute("licenseCodes",m_keywordCategoryCacheService.getLicenseCodeList());
        model.addAttribute("subjectCodes",m_keywordCategoryCacheService.getSubjectCodeList());
        return "/lecture/lecture-note";
    }

    @GetMapping("/lecture-note-member")
    public String lectureNoteDetail(@RequestParam("noteId") Long noteId, HttpServletRequest servletRequest, Model model){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return "redirect:/";
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(memberDTO == null){
            return "redirect:/member/login";
        }

        List<LectureNoteDTO> noteDTOList = m_lectureService.getLectureListByMember(memberDTO.getId());
        model.addAttribute("lectureNoteList", noteDTOList);
        model.addAttribute("noteAdd", new LectureNoteAddDTO());
        model.addAttribute("memberInfo", memberDTO);

        // 강의노트 상세정보 설정
        LectureNoteDetailDTO noteDetailDTO = m_lectureService.getLectureNoteDetailInfoByMember(memberDTO.getId(), noteId);
        model.addAttribute("noteDetail", noteDetailDTO);

        // 강의노트를 업데이트하기 위해 필요한 키워드정보들
        model.addAttribute("licenseCodes",m_keywordCategoryCacheService.getLicenseCodeList());
        model.addAttribute("subjectCodes",m_keywordCategoryCacheService.getSubjectCodeList());
        return "/lecture/lecture-note";
    }

    @PostMapping("/remove-playlist")
    public RedirectView removePlayList(@RequestParam("noteId") Long noteId, @RequestParam("playListId") Long playListId, HttpServletRequest servletRequest, RedirectAttributes attributes){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return new RedirectView("/");
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(memberDTO == null){
            return new RedirectView("/member/login");
        }
        m_lectureService.removePlayList(memberDTO.getId(), noteId, playListId);
        attributes.addAttribute("noteId", noteId);

        return new RedirectView("/lecture/lecture-note-member");
    }

    @PostMapping("/remove-book")
    public RedirectView removeBook(@RequestParam("refId") Long refId,
                                   @RequestParam("noteId") Long noteId, HttpServletRequest servletRequest, RedirectAttributes attributes){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return new RedirectView("/");
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(memberDTO == null){
            return new RedirectView("/member/login");
        }
        m_lectureService.removeBook(refId);
        attributes.addAttribute("noteId", noteId);

        return new RedirectView("/lecture/lecture-note-member");
    }

    @GetMapping("/edit-note")
    public String editNoteTemplate(@RequestParam("noteId") Long noteId, HttpServletRequest servletRequest, Model model){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return "redirect:/";
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(memberDTO == null){
            return "redirect:/member/login";
        }

        LectureNoteDTO noteDTO = m_lectureService.getLectureNoteByMember(memberDTO.getId(), noteId);
        LectureNoteEditDTO editDTO = new LectureNoteEditDTO();
        editDTO.setNoteId(noteId);
        editDTO.setNoteTitle(noteDTO.getTitle());
        editDTO.setNoteDescription(noteDTO.getDescription());
        editDTO.setLectureId(Objects.isNull(noteDTO.getLicenseId()) ? -1 : noteDTO.getLicenseId());
        editDTO.setSubjectId(Objects.isNull(noteDTO.getSubjectId()) ? -1 : noteDTO.getSubjectId());
        model.addAttribute("noteInfo", editDTO);

        // 강의노트를 업데이트하기 위해 필요한 키워드정보들
        model.addAttribute("licenseCodes",m_keywordCategoryCacheService.getLicenseCodeList());
        model.addAttribute("subjectCodes",m_keywordCategoryCacheService.getSubjectCodeList());

        return "/lecture/lecture-note-edit-modal";
    }

    @PostMapping("/edit-note")
    public RedirectView editLectureNote(@Validated @ModelAttribute("noteEditDTO")LectureNoteEditDTO noteEditDTO,
                                        BindingResult bindingResult, HttpServletRequest servletRequest, RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            log.info("강의노트 수정에러 : {}", bindingResult);
            return new RedirectView("/lecture/lecture-note");
        }
        
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return new RedirectView("/");
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(memberDTO == null){
            return new RedirectView("/member/login");
        }

        LectureNoteDTO noteDTO = new LectureNoteDTO();
        noteDTO.setId(noteEditDTO.getNoteId());
        noteDTO.setTitle(noteEditDTO.getNoteTitle());
        noteDTO.setDescription(noteEditDTO.getNoteDescription());

        m_lectureService.editLectureNote(noteDTO);
        attributes.addAttribute("noteId", noteEditDTO.getNoteId());

        return new RedirectView("/lecture/lecture-note-member");
    }

    @PostMapping("/remove-note")
    public String removeLectureNote(@RequestParam("noteId") Long noteId){
        m_lectureService.removeLectureNote(noteId);

        return "redirect:/lecture/lecture-note";
    }

    @GetMapping("modal-playlist-template")
    public String getLectureNoteListByPlayListModal(@RequestParam("memberId") Long memberId,
                                                    @RequestParam("playListId") Long playListId, Model model, HttpServletRequest servletRequest){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return "redirect:/";
        }
        List<PlayListAddModalDTO> noteDTOList = m_lectureService.getLectureNoteListByPlayListModal(memberId, playListId);
        model.addAttribute("lectureNoteList", noteDTOList);
        return "/lecture/playlist-add-modal";
    }

    @GetMapping("model-book-template")
    public String getLectureNoteListByBookModal(@RequestParam("bookId") Long bookId, Model model, HttpServletRequest servletRequest){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return "redirect:/";
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(Objects.isNull(memberDTO)){
            return "redirect:/member/login";
        }
        List<BookAddModalDTO> bookDTOList = m_lectureService.getLectureNoteListByBookModal(memberDTO.getId(), bookId);
        model.addAttribute("lectureNoteList", bookDTOList);
        return "/lecture/book-add-modal";
    }
}
