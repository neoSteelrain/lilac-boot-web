package com.steelrain.springboot.lilac.datamodel;

import lombok.Data;

/**
 * 재생목록의 영상들을 ref_tbl_lecture_youtube 테이블에 insert 하기 위해서만 사용하는 파라미터용 DTO
 */
@Data
public class PlayListVideoDTO {
    /*
    lecture_id
    lecture_member_id
    youtube_id
     */
    private Long lectureId;
    protected Long lectureMemberId;
    private Long youtubeId;
}
