package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class VideoListByPlayListEvent {
    @Setter
    private List<Long> videoDTOList;
    private Long lectureNoteId;
    private Long playListId;
    private Long memberId;
}
