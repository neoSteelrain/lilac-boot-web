package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.PageDTO;
import com.steelrain.springboot.lilac.datamodel.VideoPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class VideoPlayListSearchEvent {

    private String keyword;
    private int offset;
    private int count;

    @Setter
    private VideoPlayListSearchResultDTO searchResultDTO;
}
