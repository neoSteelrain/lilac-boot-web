package com.steelrain.springboot.lilac.exception;


public class LicenseScheduleException extends LilacException{

    public LicenseScheduleException(String msg){
        super(msg);
    }

    public LicenseScheduleException(String msg, Exception e){
        super(msg, e);
    }
}
