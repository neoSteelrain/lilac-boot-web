package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.validate.RegistrationValidateGroups.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MemberRegDTO {

    @NotBlank(groups = NickNameNotBlankCheck.class)
    @Length(min=1, max=20, groups = NickNameLengthCheck.class)
    @Pattern(regexp = "^([가-힝a-zA-Z0-9]){1,20}$", groups = NickNameRegexCheck.class)
    private String nickname;

    @NotBlank(groups = EmailNotBlankCheck.class)
    @Length(min=5,max=100, groups = EmailLengthCheck.class)
    @Email(regexp="^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}(.[a-zA-Z]{2,3})?$", groups = EmailRegexCheck.class)
    private String email;

    @NotBlank(groups = PasswordNotBlankCheck.class)
    @Length(min=6,max=19, groups = PasswordLengthCheck.class)
    @Pattern(regexp = "^([a-zA-Z0-9]){5,19}$", groups = PasswordPatternCheck.class)
    private String password;
}
