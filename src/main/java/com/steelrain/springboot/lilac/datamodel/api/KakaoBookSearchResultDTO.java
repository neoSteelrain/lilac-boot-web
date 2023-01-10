package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class KakaoBookSearchResultDTO {

    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("documents")
    private List<Documents> documents;
}
