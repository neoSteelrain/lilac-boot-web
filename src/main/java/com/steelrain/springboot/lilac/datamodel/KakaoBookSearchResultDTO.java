package com.steelrain.springboot.lilac.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class KakaoBookSearchResultDTO {

    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("documents")
    private List<Documents> documents;
}
