package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
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
    private String nickname;
    private String email;
    private String password;
    private String description;
    private Short region;
    private Integer dtlRegion;
    private String profileOriginal;
    private String profileSave;
    private Integer grade;
    private Timestamp regDate;
}
