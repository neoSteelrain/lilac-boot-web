package com.steelrain.springboot.lilac.api;

import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.exception.LectureNoteException;
import com.steelrain.springboot.lilac.exception.ValidationErrorException;
import com.steelrain.springboot.lilac.service.ILectureNoteService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/lecture/api")
public class LectureRestController {

    private final ILectureNoteService m_lectureNoteService;


    @GetMapping("/noteList")
    public ResponseEntity<LectureNoteList> getLectureNoteList(HttpServletRequest servletRequest){
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return new ResponseEntity<>(LectureNoteList.builder().noteList(new ArrayList<>(0)).build(), HttpStatus.UNAUTHORIZED);
        }
        MemberDTO dto = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        return new ResponseEntity<>(LectureNoteList.builder()
                                                .noteList(m_lectureNoteService.getLectureListByMember(dto.getId()))
                                                .build(), HttpStatus.OK);
    }

    @PostMapping("/addNote")
    public ResponseEntity<LectureAddResponse> addLectureNote(@Validated @RequestBody LectureAddRequest request,
                                                             Errors errors,
                                                             HttpServletRequest servletRequest){
        /*
            licenseId 는 자격증 id, subjectId 는 키워드 id 이다.
            licenseId, subjectId 는 XOR 관계 이기 떄문에 하나만 선택해야 한다.
            하지만 기본강의노트는 둘다 null 을 허용한다
         */
        if(errors.hasErrors() || !(request.licenseId == null ^ request.subjectId == null)){
            throw new ValidationErrorException(errors, request);
        }
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            throw new LectureNoteException("로그인 정보가 필요합니다.");
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        Long noteId = m_lectureNoteService.addLectureNote(memberDTO.getId(), request.title, request.description, request.licenseId, request.subjectId);
        if (noteId != null) {
            return new ResponseEntity<>(LectureAddResponse.builder()
                                                        .requestParameter(request)
                                                        .code(HttpStatus.OK.value())
                                                        .message("강의노트를 추가하였습니다.")
                                                        .status(HttpStatus.OK.getReasonPhrase())
                                                        .build(), HttpStatus.OK);


        }
        return new ResponseEntity<>(LectureAddResponse.builder()
                                                    .requestParameter(request)
                                                    .code(HttpStatus.CONFLICT.value())
                                                    .message("이미 존재하는 강의노트 입니다.")
                                                    .status(HttpStatus.CONFLICT.getReasonPhrase())
                                                    .build(), HttpStatus.CONFLICT);
    }

    @PostMapping("addPlayList")
    public ResponseEntity<YoutubePlayListAddResponse> addYoutubePlayListToLectureNote(@Validated @RequestBody YoutubePlayListAddRequest request,
                                                                                      Errors errors,
                                                                                      HttpServletRequest servletRequest){
        if(errors.hasErrors()){
            throw new ValidationErrorException(errors, request);
        }
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            return new ResponseEntity<>(YoutubePlayListAddResponse.builder().message("로그인 정보가 필요합니다.").build(), HttpStatus.UNAUTHORIZED);
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(m_lectureNoteService.addYoutubePlayListToLectureNote(request.lectureNoteId, request.playListId, memberDTO.getId())){
            return new ResponseEntity<>(YoutubePlayListAddResponse.builder()
                                                                .requestParameter(request)
                                                                .code(HttpStatus.OK.value())
                                                                .message("강의노트에 유튜브 재생목록을 추가하였습니다.")
                                                                .status(HttpStatus.OK.getReasonPhrase())
                                                                .build(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(YoutubePlayListAddResponse.builder()
                                                                .requestParameter(request)
                                                                .code(HttpStatus.BAD_REQUEST.value())
                                                                .message("강의노트에 유튜브 재생목록을 추가하였습니다.")
                                                                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                                                .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @Getter
    @Builder
    static class LectureNoteList{
        private List<LectureNoteDTO> noteList;
    }

    @Getter
    @ToString
    @Builder
    static class YoutubePlayListAddResponse{
        private Object requestParameter;
        private int code;
        private String message;
        private String status;
    }

    @Getter
    @ToString
    @Builder
    static class YoutubePlayListAddRequest{

        @NotNull
        private Long playListId;

        @NotNull
        private Long lectureNoteId;
    }

    @Getter
    @ToString
    @Builder
    static class LectureAddResponse{
        private Object requestParameter;
        private int code;
        private String message;
        private String status;
    }

    /*
    {
  "error": {
    "code": 400,
    "message": "Request contains an invalid argument.",
    "errors": [
      {
        "message": "Request contains an invalid argument.",
        "domain": "global",
        "reason": "badRequest"
      }
    ],
    "status": "INVALID_ARGUMENT"
  }
}
     */

    @Getter
    @ToString
    @Builder
    static class LectureAddRequest{

        private Long memberId;

        @NotBlank
        @Length(min=1, max=100)
        private String title;

        @NotBlank
        @Length(min=1, max=500)
        private String description;

        private Integer licenseId;
        private Integer subjectId;
    }
}
