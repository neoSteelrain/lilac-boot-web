package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LicenseBookListDTO {
    private String keyword;
    private String regionName;
    private String detailRegionName;

    private List<KaKaoBookDTO> kakaoBookList;
    private List<NaruLibraryDTO> libraryList;
}
