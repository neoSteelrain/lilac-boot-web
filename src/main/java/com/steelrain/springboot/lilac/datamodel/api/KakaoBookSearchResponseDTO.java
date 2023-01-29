package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class KakaoBookSearchResponseDTO {

    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("documents")
    private List<KakaoSearchedBookDTO> kakaoSearchedBookList;
}
