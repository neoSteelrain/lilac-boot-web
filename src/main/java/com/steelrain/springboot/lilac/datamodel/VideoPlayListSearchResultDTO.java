package com.steelrain.springboot.lilac.datamodel;

import com.steelrain.springboot.lilac.common.PageDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VideoPlayListSearchResultDTO {
    private int requestKeywordCode;
    private KEYWORD_TYPE requestKeywordType;
    private String searchKeyword;
    private PageDTO pageDTO;
    private List<YoutubePlayListDTO> playList;
}
