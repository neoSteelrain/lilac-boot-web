package com.steelrain.springboot.lilac.common;

import com.steelrain.springboot.lilac.datamodel.LibraryDetailRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LibraryRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseCodeDTO;
import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;

import java.util.List;

public interface ICacheService {
    List<SubjectCodeDTO> getSubjectCodeList();
    List<LibraryRegionCodeDTO> getLibraryRegionCodeList();
    List<LibraryDetailRegionCodeDTO> getLibraryDetailRegionCodeList(short regionCode);
    public List<LicenseCodeDTO> getLicenseCodeList();

    String getLicenseKeyword(int licenseCode);
    String getRegionName(short region);
    String getDetailRegionName(short region, int detailRegion);
    String getSubjectKeyword(int subjectCode);
    String getSubjectName(int subjectCode);
    String getSubjectKeywordBook(int subjectCode);
}
