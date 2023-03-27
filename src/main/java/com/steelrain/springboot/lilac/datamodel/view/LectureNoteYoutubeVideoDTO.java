package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 강의노트에 속해있는 유튜브영상 DTO
 * 강의노트에 속해있는 유튜브영상의 상세보기 페이지에 사용된다
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LectureNoteYoutubeVideoDTO extends YoutubeVideoDTO {
    private Long lectureVideoId; // 강의노트에 추가된 영상ID
    private Long lectureId; // 강의노트 ID
    private String lectureTitle;
    private Long lectureMemberId; // 회원ID
    private Long progress; // 영상의 진행률
}
