package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.view.BookDetailDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 도서의 상세정보를 요청하는 이벤트
 */
@Getter
@Builder
public class BookDetailSearchEvent {
    private Long isbn;
    private short regionCode;
    private int detailRegionCode;

    @Setter
    private BookDetailDTO bookDetailInfo;
}
