package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.exception.LectureNoteException;
import com.steelrain.springboot.lilac.service.ILectureNoteService;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture/api")
public class LectureRestController {

    private final ILectureNoteService m_lectureNoteService;

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<LectureAddResponse> handleSQLException(SQLException se){
        log.error("SQLException : {}",se);
        return new ResponseEntity<>(LectureAddResponse.builder().id(-1L).httpStatus(HttpStatus.BAD_REQUEST).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<LectureAddResponse> handleException(Exception ex){
        log.error("handleException : {}", ex);
        return new ResponseEntity<>(LectureAddResponse.builder().id(-1L).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/addNote")
    public ResponseEntity<LectureAddResponse> addLectureNote(@ModelAttribute("request") LectureAddRequest request){
        Long noteId = null;
        try {
            noteId = m_lectureNoteService.addLectureNote(request.memberId, request.title, request.description);
            if (noteId == null) {
                return new ResponseEntity<>(LectureAddResponse.builder().id(-1L).httpStatus(HttpStatus.SERVICE_UNAVAILABLE).build(), HttpStatus.SERVICE_UNAVAILABLE);
            }
        }catch (Exception ex){
            throw ex;
            /*throw new LectureNoteException(String.format("강의노트 생성 예외 발생 : 회원 ID - %s , 요청된 제목 - %s , 요청된 설명 - %s",
                    request.getMemberId(), request.getTitle(), request.getDescription()), ex);*/
        }
        return new ResponseEntity<>(LectureAddResponse.builder().id(noteId).httpStatus(HttpStatus.OK).build(), HttpStatus.OK);
    }

    @Data
    @Builder
    static class LectureAddResponse{
        private HttpStatus httpStatus;
        private Long id;
    }

    @Data
    static class LectureAddRequest{
        private Long memberId;
        private String title;
        private String description;
    }
}
