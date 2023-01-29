package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.LicenseBookListDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;

import java.util.List;
import java.util.Map;

public interface IBookService {
    LicenseBookListDTO getLicenseBookList(String keyword, short region, int detailRegion);
    List<NaruLibraryDTO> getLibraryByRegionList(short region, int detailRegion);
}
