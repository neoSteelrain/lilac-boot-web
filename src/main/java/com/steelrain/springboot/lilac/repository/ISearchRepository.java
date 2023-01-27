package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;

import java.util.List;

public interface ISearchRepository {
    List<SubjectCodeDTO> getSubjectCodes();
}
