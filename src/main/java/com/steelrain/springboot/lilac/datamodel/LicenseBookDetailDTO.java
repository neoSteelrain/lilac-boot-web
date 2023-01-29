package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class LicenseBookDetailDTO {
    private KaKaoBookDTO kaoBookDTO;
    private List<NaruLibraryDTO> libraryList;
}
