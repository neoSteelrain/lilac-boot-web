package com.steelrain.springboot.lilac.exception;

public class LilacException extends RuntimeException{

    public LilacException(String msg){
        super(msg);
    }

    public LilacException(String msg, Exception ex){
        super(msg, ex);
    }

}
