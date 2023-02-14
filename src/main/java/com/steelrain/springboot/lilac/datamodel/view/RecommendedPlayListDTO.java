package com.steelrain.springboot.lilac.datamodel.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendedPlayListDTO {
    private Long id;
    private String videoId;
    private Long channelId;
    private Long YoutubePlaylistId;
    private String title;
    private String description;
    private Timestamp publishDate;
    private Timestamp regDate;
    private String thumbnailDefault;
    private String thumbnailMedium;
    private String thumbnailHigh;
    private String playlistId;
    private Long viewCount;
    private Integer searchCount;
    private Long likeCount;
    private Long favoriteCount;
    private Long commentCount;
    private Boolean commentDisabled;
    private String duration;
    private Float score;
    private Float magnitude;
}
