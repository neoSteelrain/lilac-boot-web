package com.steelrain.springboot.lilac.api;

import com.steelrain.springboot.lilac.exception.LectureNoteException;
import com.steelrain.springboot.lilac.exception.ValidationErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<LectureRestController.LectureAddResponse> handleValidationErrorException(ValidationErrorException vex){
        log.error(String.format("잘못된 요청 예외 - 요청 파라미터 :  %s , 예외 정보 : %s", vex.getRequest(), vex));
        return new ResponseEntity<>(LectureRestController.LectureAddResponse.builder()
                .requestParameter(vex.getRequest())
                .code(HttpStatus.BAD_REQUEST.value())
                .message("처리할 수 없는 요청입니다.")
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build(), HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<LectureRestController.LectureAddResponse> handleSQLException(MethodArgumentNotValidException se){

        log.error("잘못된 요청메시지 로그 : {}",se.toString());
        return new ResponseEntity<>(LectureRestController.LectureAddResponse.builder()
                                                                            .requestParameter(null)
                                                                            .code(HttpStatus.SERVICE_UNAVAILABLE.ordinal())
                                                                            .message("처리할 수 없는 요청입니다.")
                                                                            .status(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                                                                            .build(), HttpStatus.BAD_REQUEST);
    }*/

    @ExceptionHandler(LectureNoteException.class)
    public ResponseEntity<LectureRestController.LectureAddResponse> handleLectureNoteException(LectureNoteException nex){
        log.error(String.format("강의노트 예외 발생 :  %s , 예외 정보 : %s", nex.getMessage(), nex));
        return new ResponseEntity<>(LectureRestController.LectureAddResponse.builder()
                .requestParameter(null)
                .code(HttpStatus.BAD_REQUEST.value())
                .message(nex.getMessage())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<LectureRestController.LectureAddResponse> handleException(Exception ex){
        log.error("예상하지 못한 예외 : {}", ex.toString());
        return new ResponseEntity<>(LectureRestController.LectureAddResponse.builder()
                                                                            .requestParameter(null)
                                                                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                                            .message("서버 오류입니다.")
                                                                            .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                                                            .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
