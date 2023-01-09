package com.steelrain.springboot.lilac.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
