package com.steelrain.springboot.lilac.exception;

public class LilacServiceException extends RuntimeException {
    public LilacServiceException(Exception ex){
        super(ex);
    }

    public LilacServiceException(String msg){
        super(msg);
    }
    public LilacServiceException(String msg, Exception ex){
        super(msg, ex);
    }
}
