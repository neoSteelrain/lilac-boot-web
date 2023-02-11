package com.steelrain.springboot.lilac.datamodel.view;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 회원 프로필 수정 view 전용 DTO
 */
@Getter
@Setter
public class MemberProfileEditDTO {

    @NotBlank
    @Length(min=1, max=20)
    private String nickname;

    @NotBlank
    @Email(regexp="^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}(.[a-zA-Z]{2,3})?$")
    @Length(min=5,max=100)
    private String email;

    private String description;
    private Short region;
    private Integer dtlRegion;
    private String profileOriginal;
    private String profileSave;
}
