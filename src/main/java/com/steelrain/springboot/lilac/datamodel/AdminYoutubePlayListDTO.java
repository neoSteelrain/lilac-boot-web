package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
public class AdminYoutubePlayListDTO extends YoutubePlayListDTO{
    private boolean isRecommend;
    private boolean isCandidate;
}
