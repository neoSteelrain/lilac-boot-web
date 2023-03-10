package com.steelrain.springboot.lilac.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 재생목록에 있는 영상목록을 요청하는 이벤트
 */
@Getter
@Builder
public class VideoListByPlayListEvent {
    @Setter
    private List<Long> videoDTOList;
    private Long lectureNoteId;
    private Long playListId;
    private Long memberId;
}
