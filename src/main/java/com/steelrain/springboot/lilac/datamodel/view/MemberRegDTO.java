package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.common.MEMBER_FILED_REGEXP;
import com.steelrain.springboot.lilac.validate.RegistrationValidateGroups.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
public class MemberRegDTO {

    @NotBlank(groups = NickNameNotBlankCheck.class)
    @Length(min=1, max=20, groups = NickNameLengthCheck.class)
    @Pattern(regexp = MEMBER_FILED_REGEXP.NICK_NAME, groups = NickNameRegexCheck.class)
    private String nickname;

    @NotBlank(groups = EmailNotBlankCheck.class)
    @Length(min=5,max=100, groups = EmailLengthCheck.class)
    @Email(regexp = MEMBER_FILED_REGEXP.EMAIL , groups = EmailRegexCheck.class)
    private String email;

    @NotBlank(groups = PasswordNotBlankCheck.class)
    @Length(min=6,max=19, groups = PasswordLengthCheck.class)
    @Pattern(regexp = MEMBER_FILED_REGEXP.PASSWORD, groups = PasswordPatternCheck.class)
    private String password;

    @NotNull(groups = RegionNotNullCheck.class)
    @PositiveOrZero(groups= RegionPositiveOrZeroCheck.class)
    private Short region;

    @NotNull(groups = DtlRegionNotNullCheck.class)
    @PositiveOrZero(groups = DtlRegionPositiveOrZeroCheck.class)
    private Integer dtlRegion;
}
