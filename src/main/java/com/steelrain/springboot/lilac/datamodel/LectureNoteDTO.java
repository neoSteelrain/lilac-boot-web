package com.steelrain.springboot.lilac.datamodel;

import jdk.jfr.Timespan;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
public class LectureNoteDTO {
    /*
    id	bigint	NO	PRI
    member_id	bigint	NO	PRI
    license_id	int	YES	MUL
    subject_id	int	YES	MUL
    title	varchar(100)	YES
    description	varchar(500)	YES
    reg_date	datetime	YES
    progress	int	YES
     */
    private Long id;
    private Long memberId;
    private Integer licenseId;
    private Integer subjectId;
    private String title;
    private String description;
    private Timestamp regDate;
    private Integer progress;
}
