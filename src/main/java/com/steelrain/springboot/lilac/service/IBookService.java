package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.view.LicenseBookListDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;

import java.util.List;

public interface IBookService {
    LicenseBookListDTO getLicenseBookList(String keyword, short region, int detailRegion);
    List<NaruLibraryDTO> getLibraryByRegionList(short region, int detailRegion);

    SubjectBookListDTO getSubjectBookList(int subjectCode);
}
