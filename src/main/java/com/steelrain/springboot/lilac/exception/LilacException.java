package com.steelrain.springboot.lilac.exception;

public class LilacException extends RuntimeException{
    private String exceptionCode;

    public LilacException(String msg){
        super(msg);
    }

    public LilacException(String msg, String code){
        super(msg);
        this.exceptionCode = code;
    }

    public LilacException(String msg, Exception ex){
        super(msg, ex);
    }

    public LilacException(String msg, Exception ex, String code){
        super(msg, ex);
        this.exceptionCode = code;
    }
}
