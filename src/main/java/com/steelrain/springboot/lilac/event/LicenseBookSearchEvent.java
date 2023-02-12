package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.view.LicenseBookListDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
