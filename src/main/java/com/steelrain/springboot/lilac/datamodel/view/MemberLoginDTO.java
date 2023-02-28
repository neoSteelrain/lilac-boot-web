package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.validate.LoginValidateGroups.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class MemberLoginDTO {

    @NotBlank( groups = EmailNotBlankCheck.class)
    @Email(regexp="^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}(.[a-zA-Z]{2,3})?$", groups = EmailRegexCheck.class)
    @Length(min=5,max=100, groups = EmailLengthCheck.class)
    private String email;

    @NotBlank( groups = NickNameNotBlankCheck.class)
    @Length(min=6,max=19, groups = NickNameLengthCheck.class)
    @Pattern(regexp = "^([a-zA-Z0-9]){5,19}$", groups = NickNamePatternCheck.class)
    private String password;
}
