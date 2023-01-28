package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.LibraryDetailRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LibraryRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseCodeDTO;
import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;

import java.util.List;

public interface ISearchRepository {
    List<SubjectCodeDTO> getSubjectCodes();
    List<LibraryRegionCodeDTO> getLibRegionCodes();
    List<LibraryDetailRegionCodeDTO> getLibDetailRegionCodes(int regionCode);
    public List<LicenseCodeDTO> getLicenseCodes();
}
