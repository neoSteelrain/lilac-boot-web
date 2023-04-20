package com.steelrain.springboot.lilac.exception;

public class LilacRepositoryException extends Exception {
    public LilacRepositoryException(Exception ex){
        super(ex);
    }

    public LilacRepositoryException(String msg, Exception ex){
        super(msg, ex);
    }
}
