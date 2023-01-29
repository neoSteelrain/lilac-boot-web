package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class MemberDTO {
    /*
    id	bigint	NO	PRI
    nickname	varchar(20)	YES	UNI
    email	varchar(100)	YES	UNI
    password	varchar(20)	YES
    description	varchar(1000)	YES
    region	smallint	YES
    dtl_region	int	YES
    profile_original	varchar(255)	YES
    profile_save	varchar(255)	YES
    grade	int	YES
    reg_date	datetime	YES
     */
    private Long id;

    @NotBlank
    @Length(min=1, max=20)
    private String nickname;

    @NotBlank
    @Email(regexp="^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}(.[a-zA-Z]{2,3})?$")
    @Length(min=5,max=100)
    private String email;

    @NotBlank
    @Length(min=6,max=19)
    private String password;

    private String description;
    private Short region;
    private Integer dtlRegion;
    private String profileOriginal;
    private String profileSave;
    private Timestamp regDate;
    private Integer grade;
}
