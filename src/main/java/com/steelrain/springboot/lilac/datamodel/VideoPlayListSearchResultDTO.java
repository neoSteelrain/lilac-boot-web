package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VideoPlayListSearchResultDTO {
    private String requestKeyword;
    private PageDTO pageDTO;
    private List<YoutubePlayListDTO> playList;
}
