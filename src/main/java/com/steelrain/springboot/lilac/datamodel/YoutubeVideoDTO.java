package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Getter
@ToString
public class YoutubeVideoDTO {
/*
id	bigint	NO	PRI
video_id	varchar(50)	NO	UNI
channel_id	bigint	NO	MUL
youtube_playlist_id	bigint	YES	MUL
title	varchar(100)	NO	MUL
description	varchar(5000)	YES
publish_date	datetime	YES
reg_date	datetime	YES
thumbnail_default	varchar(255)	YES
thumbnail_medium	varchar(255)	YES
thumbnail_high	varchar(255)	YES
playlist_id	varchar(50)	YES
view_count	bigint	YES
search_count	int	YES
like_count	bigint	YES
favorite_count	bigint	YES
comment_count	bigint	YES
comment_disabled	tinyint(1)	YES
duration	varchar(40)	YES
score	float	YES
magnitude	float	YES
 */
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
}
