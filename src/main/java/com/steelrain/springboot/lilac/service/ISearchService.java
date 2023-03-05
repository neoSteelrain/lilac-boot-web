package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.datamodel.view.BookDetailDTO;
import com.steelrain.springboot.lilac.datamodel.view.KeywordBookListDTO;
import com.steelrain.springboot.lilac.datamodel.view.LicenseBookListDTO;
import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;

import java.util.List;

public interface ISearchService {

    List<SubjectCodeDTO> getSubjectCodes();
    List<LibraryRegionCodeDTO> getLibRegionCodes();
    List<LibraryDetailRegionCodeDTO> getLibDetailRegionCodes(int regionCode);
    LicenseDTO getLicenseInfoByCode(int licenseCode);
    LicenseBookListDTO getLicenseBookList(int licenseCode, short regionCode, int detailRegionCode, int pageNum, int bookCount);
    VideoPlayListSearchResultDTO searchPlayList(int keywordCode, String searchKeyword, int pageNum, int playlistCount , int keywordType);
    //VideoPlayListSearchResultDTO searchPlayList(int keywordCode, String searchKeyword, int pageNum, int playlistCount , SEARCH_KEYWORD_TYPE keywordType);
    VideoPlayListSearchResultDTO searchPlayList(String keyword, int pageNum, int playlistCount);
    SubjectBookListDTO getSubjectBookList(int subjectCode, int pageNum, int bookCount);
    BookDetailDTO getBookDetailInfo(Long bookId, short regionCode, int detailRegionCode);
    SubjectBookListDTO getSubjectBookList(String keyword, int pageNum, int bookCount);
}
