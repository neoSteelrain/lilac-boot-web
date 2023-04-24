package com.steelrain.springboot.lilac.datamodel;

import lombok.Data;

@Data
public class YoutubeVideoProgressDTO {
    private String videoId;
    private long progress;
    private String duration;
    private double progressRate;
}
