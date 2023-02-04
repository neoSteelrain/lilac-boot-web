package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendedVideoDTO {
    private Long id;
    private Long YoutubePlaylistId;
    private String title;
    private String thumbnailMedium;
    private Long viewCount;
    private Integer searchCount;
    private Long likeCount;
    private Long favoriteCount;
    private String channelTitle;
}
