package com.steelrain.springboot.lilac.validate;

import javax.validation.GroupSequence;
import com.steelrain.springboot.lilac.validate.LoginValidateGroups.*;

/**
 * 로그인 빈 검증 순서
 */
@GroupSequence({EmailNotBlankCheck.class,
                EmailRegexCheck.class,
                EmailLengthCheck.class,
                NickNameNotBlankCheck.class,
                NickNameLengthCheck.class,
                NickNamePatternCheck.class})
public interface LoginValidationSequence {
}
