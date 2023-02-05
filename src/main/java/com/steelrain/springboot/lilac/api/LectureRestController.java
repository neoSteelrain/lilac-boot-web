package com.steelrain.springboot.lilac.api;

import com.steelrain.springboot.lilac.config.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.exception.LectureNoteException;
import com.steelrain.springboot.lilac.exception.ValidationErrorException;
import com.steelrain.springboot.lilac.service.ILectureNoteService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
    public ResponseEntity<LectureAddResponse> addLectureNote(@Validated @RequestBody LectureAddRequest request, Errors errors, HttpServletRequest servletRequest){
        if(errors.hasErrors()){
            throw new ValidationErrorException(errors, request);
        }
        HttpSession session = servletRequest.getSession(false);
        if(session == null){
            throw new LectureNoteException("로그인 정보가 필요합니다.");
        }
        MemberDTO dto = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        Long noteId = m_lectureNoteService.addLectureNote(dto.getId(), request.title, request.description);
        if (noteId == null) {
            return new ResponseEntity<>(LectureAddResponse.builder()
                                                        .requestParameter(request)
                                                        .code(HttpStatus.CONFLICT.value())
                                                        .message("이미 존재하는 강의노트 입니다.")
                                                        .status(HttpStatus.CONFLICT.getReasonPhrase())
                                                        .build(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(LectureAddResponse.builder()
                                                    .requestParameter(request)
                                                    .code(HttpStatus.OK.value())
                                                    .message("강의노트를 추가하였습니다.")
                                                    .status(HttpStatus.OK.getReasonPhrase())
                                                    .build(), HttpStatus.OK);
    }

    @Getter
    @Builder
    static class LectureNoteList{
        private List<LectureNoteDTO> noteList;
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

        @NotNull
        private Long memberId;

        @NotBlank
        private String title;

        @NotBlank
        private String description;
    }
}
