package com.steelrain.springboot.lilac.exception;

public class InvalidScheduleJsonException extends LilacException{

    private int m_licenseCode;
    private String m_jsonStr;

    public InvalidScheduleJsonException(String msg, String jsonStr, int licenseCode){
        super(msg);
        m_jsonStr = jsonStr;
        m_licenseCode = licenseCode;
    }

    public InvalidScheduleJsonException(String msg, Exception e, String jsonStr, int licenseCode) {
        super(msg, e);
        m_jsonStr = jsonStr;
        m_licenseCode = licenseCode;
    }
}
