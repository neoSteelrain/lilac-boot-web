package com.steelrain.springboot.lilac.validate;

/**
 * 로그인 빈 검증 마커인터페이스
 */
public interface LoginValidateGroups {
    public interface EmailNotBlankCheck {};
    public interface EmailRegexCheck {};
    public interface EmailLengthCheck {};
    public interface NickNameNotBlankCheck {};
    public interface NickNameLengthCheck {};
    public interface NickNamePatternCheck {};
}
