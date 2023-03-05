package com.steelrain.springboot.lilac.datamodel.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeywordBookListDTO extends BookSearchResultDTO{
    private String keyword;
}
