package com.steelrain.springboot.lilac.exception;

public class KakaoBookSearchException extends LilacException{

    public KakaoBookSearchException(String msg){
        super(msg);
    }

    public KakaoBookSearchException(String msg, Exception ex){
        super(msg, ex);
    }
}
