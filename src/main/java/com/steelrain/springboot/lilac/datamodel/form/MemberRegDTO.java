package com.steelrain.springboot.lilac.datamodel.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MemberRegDTO {

    @NotBlank
    @Length(min=1, max=20)
    private String nickname;

    @NotBlank
    @Email(regexp="^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}(.[a-zA-Z]{2,3})?$")
    @Length(min=5,max=100)
    private String email;

    @NotBlank
    @Length(min=6,max=19)
    @Pattern(regexp = "^[A-Za-z\\d\\[\\]\\{\\}\\/\\(\\)\\.\\?\\<\\>!@#$%^&*=+-]{6,20}")
    private String password;
}
