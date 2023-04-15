package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp publishDate;
    private String thumbnailMedium;
    private String thumbnailHigh;
    private Integer itemCount;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp regDate;
    private String channelTitle;
}
