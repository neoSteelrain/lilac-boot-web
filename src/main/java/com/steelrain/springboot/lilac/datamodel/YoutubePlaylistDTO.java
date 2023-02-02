package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class YoutubePlaylistDTO {
    /*
    id	bigint	NO	PRI
    playlist_id	varchar(50)	NO	UNI
    channel_id	bigint	NO	MUL
    title	varchar(100)	YES
    publish_date	datetime	YES
    thumbnail_medium	varchar(255)	YES
    thumbnail_high	varchar(255)	YES
    item_count	int	YES
    reg_date	datetime	YES
     */
    private Long id;
    private String playlistId;
    private Long channelId;
    private String title;
    private Timestamp publishDate;
    private String thumbnailMedium;
    private String thumbnailHigh;
    private Integer itemCount;
    private Timestamp regDate;
}
