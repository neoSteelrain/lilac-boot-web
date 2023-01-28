package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class LicenseSearchEvent {
    private int code;
    private String keyWord;

    @Setter
    private LicenseDTO licenseDTO;
}
