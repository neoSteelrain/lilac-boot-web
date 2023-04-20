package com.steelrain.springboot.lilac.exception;

public class AwsS3RepositoryException extends RuntimeException {
    private Object m_sourceObject;

    public AwsS3RepositoryException(String msg){
        super(msg);
    }

    public AwsS3RepositoryException(String msg, Exception ex){
        super(msg, ex);
    }

    public AwsS3RepositoryException(String msg, Exception ex, Object srcObject){
        super(msg, ex);
        m_sourceObject = srcObject;
    }
}
