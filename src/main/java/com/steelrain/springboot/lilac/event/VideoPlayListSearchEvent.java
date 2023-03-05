package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.SEARCH_KEYWORD_TYPE;
import com.steelrain.springboot.lilac.datamodel.VideoPlayListSearchResultDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class VideoPlayListSearchEvent {

    private String keyword;
    private int keywordCode;
    private int pageNum;
    private int playlistCount;
    private int keywordType;
    //private SEARCH_KEYWORD_TYPE keywordType;

    @Setter
    private VideoPlayListSearchResultDTO searchResultDTO;
}
