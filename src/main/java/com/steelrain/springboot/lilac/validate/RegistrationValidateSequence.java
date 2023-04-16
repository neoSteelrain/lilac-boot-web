package com.steelrain.springboot.lilac.validate;

import javax.validation.GroupSequence;
import com.steelrain.springboot.lilac.validate.RegistrationValidateGroups.*;

/**
 * 회원가입 빈 검증 순서
 */
@GroupSequence({NickNameNotBlankCheck.class,
                NickNameLengthCheck.class,
                NickNameRegexCheck.class,
                EmailNotBlankCheck.class,
                EmailRegexCheck.class,
                EmailLengthCheck.class,
                PasswordNotBlankCheck.class,
                PasswordLengthCheck.class,
                PasswordPatternCheck.class,
                RegionNotNullCheck.class,
                RegionPositiveOrZeroCheck.class})
public interface RegistrationValidateSequence {
}
