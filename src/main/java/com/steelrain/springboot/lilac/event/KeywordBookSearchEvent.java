package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.view.KeywordBookListDTO;
import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class KeywordBookSearchEvent {
    private String keyword;
    private short regionCode;
    private int detailRegionCode;
    private int pageNum;
    private int bookCount;

    @Setter
    private SubjectBookListDTO keywordBookListDTO;
}
