package com.steelrain.springboot.lilac.exception;


public class NaruAPIQuotaOverException extends Exception{

    public NaruAPIQuotaOverException(Exception ex) {
        super(ex);
    }

    public NaruAPIQuotaOverException(String msg){
        super(msg);
    }

    public NaruAPIQuotaOverException(String msg, Exception ex){
        super(msg, ex);
    }
}
