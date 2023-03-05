package com.steelrain.springboot.lilac.common;

/**
 * 회원DTO의 빈검증 할때 사용하는 정규식을 문자열 상수로 가지고 있는 클래스
 */
public class MEMBER_FILED_REGEXP {
    public final static String NICK_NAME = "^([가-힝a-zA-Z0-9]){1,20}$";
    public final static String EMAIL = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}(.[a-zA-Z]{2,3})?$";
    public final static String PASSWORD = "^([a-zA-Z0-9]){5,19}$";

    private MEMBER_FILED_REGEXP(){}
}
