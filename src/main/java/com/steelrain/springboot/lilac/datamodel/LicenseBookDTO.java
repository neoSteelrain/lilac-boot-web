package com.steelrain.springboot.lilac.datamodel;

import com.steelrain.springboot.lilac.datamodel.api.KakaoSearchedBookDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByBookResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LicenseBookDTO {
    private KaKaoBookDTO kakaoBookDTO;
    private List<NaruLibraryDTO> libraryList;
}
