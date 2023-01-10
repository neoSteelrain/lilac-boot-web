package com.steelrain.springboot.lilac.datamodel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MemberDTO {
    /*
    id	bigint
    nickname	varchar(20)
    email	varchar(100)
    password	varchar(20)
    description	varchar(500)
    region	char(2)
    dtl_region	char(5)
    profile_original	varchar(255)
    profile_save	varchar(255)
    reg_date	datetime
    grade	int
     */
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String description;
    private String region;
    private String dtlRegion;
    private String profileOriginal;
    private String profileSave;
    private Timestamp regDate;
    private Integer grade;
}
