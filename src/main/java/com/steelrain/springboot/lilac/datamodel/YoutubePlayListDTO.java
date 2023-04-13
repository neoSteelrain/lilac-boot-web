package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class YoutubePlayListDTO {
    private Long id;
    private String playlistId;
    private Long channelId;
    private Integer licenseId;
    private Integer subjectId;
    private String title;
    private Timestamp publishDate;
    private String thumbnailMedium;
    private String thumbnailHigh;
    private Integer itemCount;
    private Timestamp regDate;
    private String channelTitle;
}
