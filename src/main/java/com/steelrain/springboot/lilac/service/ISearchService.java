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
    LicenseBookListDTO getLicenseBookList(int licenseCode, short regionCode, int detailRegionCode, int pageNum, int bookCount);
    VideoPlayListSearchResultDTO searchPlayList(int keywordCode, int pageNum, int playlistCount , int keywordType);

    SubjectBookListDTO getSubjectBookList(int subjectCode, int pageNum, int bookCount);
}
