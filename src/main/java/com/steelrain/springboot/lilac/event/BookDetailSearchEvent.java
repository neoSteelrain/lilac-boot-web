package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.view.BookDetailDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class BookDetailSearchEvent {
    private Long bookId;
    private short regionCode;
    private int detailRegionCode;

    @Setter
    private BookDetailDTO bookDetailInfo;
}
