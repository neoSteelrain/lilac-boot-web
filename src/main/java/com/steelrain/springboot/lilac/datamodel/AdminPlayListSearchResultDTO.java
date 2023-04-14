package com.steelrain.springboot.lilac.datamodel;

import com.steelrain.springboot.lilac.common.PageDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdminPlayListSearchResultDTO {
    private PageDTO pageDTO;
    private List<AdminYoutubePlayListDTO> playlist;
}
