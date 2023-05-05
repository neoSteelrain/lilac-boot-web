package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.common.AlertRedirectUtil;
import com.steelrain.springboot.lilac.common.ICacheService;
import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.datamodel.view.*;
import com.steelrain.springboot.lilac.service.ILectureNoteService;
import com.steelrain.springboot.lilac.service.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("lecture")
public class LectureController {

    private final ILectureNoteService m_lectureService;
    private final IMemberService m_memberService;
    private final ICacheService m_keywordCategoryCacheService;


    @GetMapping("/lecture-note-member")
    public String lectureNoteDetail(@RequestParam(value = "noteId", required = false) Long noteId, HttpSession session, Model model){
        MemberDTO memberDTO = m_memberService.getMemberInfo((Long)session.getAttribute(SESSION_KEY.MEMBER_ID));
        List<LectureNoteDTO> noteDTOList = m_lectureService.getLectureListByMember(memberDTO.getId());
        model.addAttribute("lectureNoteList", noteDTOList);
        model.addAttribute("noteAdd", new LectureNoteAddDTO());
        model.addAttribute("memberInfo", memberDTO);

        // 강의노트를 업데이트하기 위해 필요한 키워드정보들
        model.addAttribute("licenseCodes",m_keywordCategoryCacheService.getLicenseCodeList());
        model.addAttribute("subjectCodes",m_keywordCategoryCacheService.getSubjectCodeList());

        // 강의노트 상세정보 설정
        Long currentNoteId = null;
        if(noteDTOList.size() == 0){
            model.addAttribute("noteDetail", new LectureNoteDetailDTO() );
        }else{
            currentNoteId = Objects.isNull(noteId) ? noteDTOList.get(0).getId() : noteId;
            model.addAttribute("noteDetail", m_lectureService.getLectureNoteDetailInfoByMember(memberDTO.getId(), currentNoteId));
        }
        model.addAttribute("currentNoteId", currentNoteId);
        return "lecture/lecture-note";
    }

    @PostMapping("/remove-playlist")
    public RedirectView removePlayList(@RequestParam("noteId") Long noteId,
                                       @RequestParam("playListId") Long playListId,
                                       HttpSession session, RedirectAttributes attributes){
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        m_lectureService.removePlayList(memberId, noteId, playListId);
        attributes.addAttribute("noteId", noteId);

        return new RedirectView("lecture-note-member");
    }

    @PostMapping("/remove-book")
    public RedirectView removeBook(@RequestParam("refId") Long refId,
                                   @RequestParam("noteId") Long noteId,
                                   RedirectAttributes attributes){
        m_lectureService.removeBook(refId);
        attributes.addAttribute("noteId", noteId);

        return new RedirectView("lecture-note-member");
    }

    @GetMapping("/edit-note")
    public String editNoteTemplate(@RequestParam("noteId") Long noteId, HttpSession session, Model model){
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        LectureNoteDTO noteDTO = m_lectureService.getLectureNoteByMember(memberId, noteId);
        LectureNoteEditDTO editDTO = new LectureNoteEditDTO();
        editDTO.setNoteId(noteId);
        editDTO.setNoteTitle(noteDTO.getTitle());
        editDTO.setNoteDescription(noteDTO.getDescription());
        editDTO.setLicenseId(Objects.isNull(noteDTO.getLicenseId()) ? -1 : noteDTO.getLicenseId());
        editDTO.setSubjectId(Objects.isNull(noteDTO.getSubjectId()) ? -1 : noteDTO.getSubjectId());
        model.addAttribute("noteInfo", editDTO);

        // 강의노트를 업데이트하기 위해 필요한 키워드정보들
        model.addAttribute("licenseCodes",m_keywordCategoryCacheService.getLicenseCodeList());
        model.addAttribute("subjectCodes",m_keywordCategoryCacheService.getSubjectCodeList());

        /*
            1. 강의노트 수정하기 위해 모달창을 만드는 페이지로 forward
            2. 모달창에서 수정을 마친다
            3. 모달창에서는 수정정보를 edit-note 로 post 요청을 해서 수정을 완료 한다
         */
        return "lecture/lecture-note-edit-modal";
    }

    @PostMapping("/edit-note")
    public String editLectureNote(@Validated @ModelAttribute("noteEditDTO")LectureNoteEditDTO noteEditDTO,
                                        BindingResult bindingResult, RedirectAttributes attributes, Model model){
        if(bindingResult.hasErrors()){
            log.info("강의노트 수정에러 : {}", bindingResult);
            attributes.addAttribute("noteId", noteEditDTO.getNoteId());
            return "redirect:lecture-note-member";
        }
        LectureNoteDTO noteDTO = new LectureNoteDTO();
        noteDTO.setId(noteEditDTO.getNoteId());
        noteDTO.setTitle(noteEditDTO.getNoteTitle());
        noteDTO.setDescription(noteEditDTO.getNoteDescription());
        noteDTO.setLicenseId(noteEditDTO.getLicenseId());
        noteDTO.setSubjectId(noteEditDTO.getSubjectId());

        try{
            m_lectureService.editLectureNote(noteDTO);
            attributes.addAttribute("noteId", noteEditDTO.getNoteId());
            return "redirect:lecture-note-member";
        }catch(Exception ex){
            log.error("강의노트 수정 중 예외발생 - 강의노트정보 - {}, 예외정보 - {]", noteDTO, ex);
            return AlertRedirectUtil.alertRedirect("강의노트 수정도중 오류가 발생했습니다", "lecture-note-member", model);
        }
    }

    @PostMapping("/remove-note")
    public String removeLectureNote(@RequestParam("noteId") Long noteId, HttpSession session, Model model){
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        try{
            LectureNoteStatus lecStatus = m_lectureService.removeLectureNote(noteId, (Long) session.getAttribute(SESSION_KEY.MEMBER_ID));
            if(lecStatus.isLast()){
                return AlertRedirectUtil.alertRedirect("강의노트는 전부 삭제할 수 없습니다", "lecture-note-member", model);
            }
            return "redirect:lecture-note-member";
        }catch(Exception ex){
            log.error("강의노트 삭제중 예외 발생 : 강의노트ID - {}, 회원ID - {}, 예외정보 - {}", noteId, memberId, ex);
            return AlertRedirectUtil.alertRedirect("강의노트 삭제도중 오류가 발생했습니다", "lecture-note-member", model);
        }
    }

    @GetMapping("modal-playlist-template")
    public String getLectureNoteListByPlayListModal(@RequestParam("memberId") Long memberId,
                                                    @RequestParam("playListId") Long playListId, Model model){
        List<PlayListAddModalDTO> noteDTOList = m_lectureService.getLectureNoteListByPlayListModal(memberId, playListId);
        model.addAttribute("lectureNoteList", noteDTOList);
        return "lecture/playlist-add-modal";
    }

    @GetMapping("model-book-template")
    public String getLectureNoteListByBookModal(@RequestParam("bookId") Long bookId, Model model, HttpSession session){
        Long memberId = (Long)session.getAttribute(SESSION_KEY.MEMBER_ID);
        List<BookAddModalDTO> bookDTOList = m_lectureService.getLectureNoteListByBookModal(memberId, bookId);
        model.addAttribute("lectureNoteList", bookDTOList);
        return "lecture/book-add-modal";
    }
}
