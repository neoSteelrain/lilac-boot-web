package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자가 직접 입력한 키워드로 도서를 검색하는 이벤트
 */
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
