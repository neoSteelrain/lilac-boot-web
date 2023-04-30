package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.common.MEMBER_FILED_REGEXP;
import com.steelrain.springboot.lilac.validate.ProfileImageFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.sql.Timestamp;

/**
 * 회원 프로필 수정 view 전용 DTO
 */
@Getter
@Setter
@ToString
public class MemberProfileEditDTO {

    private Long Id;

    @NotBlank
    @Length(min=1, max=20)
    @Pattern(regexp = MEMBER_FILED_REGEXP.NICK_NAME)
    private String nickname;

    @NotBlank
    @Length(min=5,max=100)
    @Email(regexp = MEMBER_FILED_REGEXP.EMAIL)
    private String email;

    @Nullable
    @Length(min=0, max=1000)
    private String description;

    @Nullable
    private Short region;

    @Nullable
    private Integer dtlRegion;

    @Nullable
    @Length(min=0, max=255)
    private String profileOriginal;

    @ProfileImageFormat
    private MultipartFile profileImage;

    @Nullable
    @Length(min=0, max=2048)
    private String profileSave;

    private Timestamp regDate;
}
