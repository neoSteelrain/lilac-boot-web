package com.steelrain.springboot.lilac.datamodel;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String description;
    private String profileOriginal;
    private String profileSave;
    private Integer grade;
    private Timestamp regDate;
    private Short region;
    private Integer dtlRegion;
}
