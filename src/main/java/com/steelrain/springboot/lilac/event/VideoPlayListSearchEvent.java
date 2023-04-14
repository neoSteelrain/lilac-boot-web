package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.VideoPlayListSearchResultDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 영상정보 검색을 요청하는 이벤트
 */
@Getter
@Builder
public class VideoPlayListSearchEvent {

    protected String keyword;
    private int keywordCode;
    private int pageNum;
    private int playlistCount;
    private int keywordType;
    //private SEARCH_KEYWORD_TYPE keywordType;

    @Setter
    private VideoPlayListSearchResultDTO searchResultDTO;
}
