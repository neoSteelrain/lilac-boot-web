package com.steelrain.springboot.lilac.validate;

/**
 * 회원가입 빈 검증 그룹 마커인터페이스
 */
public interface RegistrationValidateGroups {
    public interface NickNameNotBlankCheck {};
    public interface NickNameLengthCheck {};
    public interface NickNameRegexCheck {};
    public interface EmailNotBlankCheck {};
    public interface EmailLengthCheck {};
    public interface EmailRegexCheck {};
    public interface PasswordNotBlankCheck {};
    public interface PasswordLengthCheck {};
    public interface PasswordPatternCheck {};
    public interface RegionNotNullCheck {};
    public interface RegionPositiveOrZeroCheck{};
}
