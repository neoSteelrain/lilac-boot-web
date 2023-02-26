package com.steelrain.springboot.lilac.validate;

import javax.validation.GroupSequence;
import com.steelrain.springboot.lilac.validate.LoginValidateGroups.*;

@GroupSequence({EmailNotBlankGroup.class, EmailRegexGroup.class, EmailLengthGroup.class, NickNameNotBlankGroup.class, NickNameLengthGroup.class, NickNamePatternGroup.class})
public interface LoginValidationSequence {
}
