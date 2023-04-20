package com.steelrain.springboot.lilac.exception;


public class LicenseScheduleException extends RuntimeException {

    private int m_licenseCode;

    public LicenseScheduleException(String msg, int licenseCode){
        super(msg);
        m_licenseCode = licenseCode;
    }

    public LicenseScheduleException(String msg, Exception e, int licenseCode){
        super(msg, e);
        m_licenseCode = licenseCode;
    }
}
