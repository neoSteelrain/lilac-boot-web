package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.view.LicenseBookListDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 자격증에 관련된 도서검색을 요청하는 이벤트
 */
@Getter
@Builder
public class LicenseBookSearchEvent {
    private int licenseCode;
    private short regionCode;
    private int detailRegionCode;
    private int pageNum;
    private int licenseBookCount;

    @Setter
    private LicenseBookListDTO licenseBookListDTO;
}
