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
