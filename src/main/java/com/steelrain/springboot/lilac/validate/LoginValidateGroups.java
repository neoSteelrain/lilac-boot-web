package com.steelrain.springboot.lilac.validate;

public class LoginValidateGroups {
    public interface EmailNotBlankGroup {};
    public interface EmailRegexGroup {};
    public interface EmailLengthGroup{};
    public interface NickNameNotBlankGroup{};
    public interface NickNameLengthGroup{};
    public interface NickNamePatternGroup{};
}
