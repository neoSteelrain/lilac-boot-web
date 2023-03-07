package com.steelrain.springboot.lilac.exception;

/**
 * 라일락 사이트의 기본 예외 클래스
 */
public class LilacException extends RuntimeException{

    public LilacException(String msg){
        super(msg);
    }

    public LilacException(String msg, Exception ex){
        super(msg, ex);
    }

}
