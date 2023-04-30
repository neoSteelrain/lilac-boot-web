package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminYoutubePlayListDTO extends YoutubePlayListDTO{
    private boolean isRecommend;
    private boolean isCandidate;
    private Integer totalLike;
    private Integer viewCount;
}
