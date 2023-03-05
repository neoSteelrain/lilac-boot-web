package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.view.BookDetailDTO;
import com.steelrain.springboot.lilac.datamodel.view.LicenseBookListDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedBookListDTO;
import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;

import java.util.List;

public interface IBookService {
    LicenseBookListDTO getLicenseBookList(int licenseCode, short regionCode, int detailRegionCode, int pageNum, int bookCount);
    List<NaruLibraryDTO> getLibraryByRegionList(short region, int detailRegion);
    SubjectBookListDTO getSubjectBookList(int subjectCode, int pageNum, int bookCount);
    //SubjectBookListDTO getSubjectBookList(String keyword, int pageNum, int bookCount);
    BookDetailDTO getBookDetailInfo(Long isbn, short regionCode, int detailRegionCode);
    RecommendedBookListDTO getRecommendedBookList();
}
