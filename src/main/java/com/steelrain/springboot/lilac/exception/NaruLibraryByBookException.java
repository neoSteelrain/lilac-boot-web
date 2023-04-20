package com.steelrain.springboot.lilac.exception;


public class NaruLibraryByBookException extends RuntimeException {

    public NaruLibraryByBookException(String msg){
        super(msg);
    }

    public NaruLibraryByBookException(String msg, Exception ex){
        super(msg, ex);
    }
}
