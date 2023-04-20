package com.steelrain.springboot.lilac.exception;

public class DuplicateLilacMemberException extends Exception {

    public DuplicateLilacMemberException(Exception ex){
        super(ex);
    }
    public DuplicateLilacMemberException(String msg, Exception ex){
        super(msg, ex);
    }
}
