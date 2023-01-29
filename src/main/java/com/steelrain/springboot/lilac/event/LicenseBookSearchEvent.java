package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.LicenseBookListDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class LicenseBookSearchEvent {
    private String keyword;
    private short region;
    private int detailRegion;

    @Setter
    private LicenseBookListDTO licenseBookListDTO;
}
