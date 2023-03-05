package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import com.steelrain.springboot.lilac.common.PageDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LicenseBookListDTO {
    private int licenseCode;
    private String keyword;
    private int regionCode;
    private int detailRegionCode;
    private String regionName;
    private String detailRegionName;

    private int totalBookCount;
    private List<KaKaoBookDTO> kakaoBookList;
    private List<NaruLibraryDTO> libraryList;
    private PageDTO pageInfo;
}
