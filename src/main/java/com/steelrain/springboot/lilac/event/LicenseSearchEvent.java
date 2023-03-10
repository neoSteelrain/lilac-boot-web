package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 자격증정보를 요청하는 이벤트
 */
@Getter
@Builder
public class LicenseSearchEvent {
    private int code;
    private String keyWord;

    @Setter
    private LicenseDTO licenseDTO;
}
