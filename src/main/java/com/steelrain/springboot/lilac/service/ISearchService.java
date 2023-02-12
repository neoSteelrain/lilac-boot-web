package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.datamodel.view.LicenseBookListDTO;
import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;

import java.util.List;

public interface ISearchService {

    List<SubjectCodeDTO> getSubjectCodes();
    List<LibraryRegionCodeDTO> getLibRegionCodes();
    List<LibraryDetailRegionCodeDTO> getLibDetailRegionCodes(int regionCode);

    LicenseDTO getLicenseInfoByCode(int licenseCode);
    LicenseBookListDTO getLicenseBookList(String keyword, short region, int detailRegion);
    VideoPlayListSearchResultDTO searchPlayList(String keyword, int offset, int count);

    SubjectBookListDTO getSubjectBookList(int subjectCode);
}
