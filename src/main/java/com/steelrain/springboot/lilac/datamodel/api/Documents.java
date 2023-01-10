package com.steelrain.springboot.lilac.datamodel.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
public class Documents {
    @JsonProperty("url")
    private String url;
    @JsonProperty("translators")
    private List<String> translators;
    @JsonProperty("title")
    private String title;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("status")
    private String status;
    @JsonProperty("sale_price")
    private int salePrice;
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("price")
    private int price;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("datetime")
    private String datetime;
    @JsonProperty("contents")
    private String contents;
    @JsonProperty("authors")
    private List<String> authors;
}
