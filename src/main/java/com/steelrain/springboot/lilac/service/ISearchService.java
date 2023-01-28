package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.*;

import java.util.List;

public interface ISearchService {

    List<SubjectCodeDTO> getSubjectCodes();
    List<LibraryRegionCodeDTO> getLibRegionCodes();
    List<LibraryDetailRegionCodeDTO> getLibDetailRegionCodes(int regionCode);

    LicenseDTO getLicenseInfoByCode(int licenseCode);
    List<LicenseBookDTO> getLicenseBookList(String keyword, short region, int detailRegion);
}
