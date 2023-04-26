package com.steelrain.springboot.lilac.datamodel;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class YoutubeVideoProgressDTO {
    private String videoId;
    private long progress;
    private String duration;
    private double progressRate;
}
