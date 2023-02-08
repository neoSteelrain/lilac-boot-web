package com.steelrain.springboot.lilac.datamodel;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureNoteDTO {
    private Long id;
    private Long memberId;
    private Integer licenseId;
    private Integer subjectId;
    private String title;
    private String description;
    private Timestamp regDate;
    private Integer progress;
}
