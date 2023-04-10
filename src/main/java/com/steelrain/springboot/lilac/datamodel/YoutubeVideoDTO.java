package com.steelrain.springboot.lilac.datamodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

/**
 * 유튜브영상정보 DTO
 * tbl_youtube 테이블에 매핑된다
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class YoutubeVideoDTO {
    private Long id;
    private String videoId;
    private Long channelId;
    private Long YoutubePlaylistId;
    private String title;
    private String description;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp publishDate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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

    private Long lilacLikeCount;
    private Long lilacDislikeCount;
}
