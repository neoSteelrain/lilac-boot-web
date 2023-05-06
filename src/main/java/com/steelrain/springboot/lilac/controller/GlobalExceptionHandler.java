package com.steelrain.springboot.lilac.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 글로벌 예외 핸들러
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex){
        log.error("전역에러 발생 : {}", ex);
        return "error/error";
    }
}
