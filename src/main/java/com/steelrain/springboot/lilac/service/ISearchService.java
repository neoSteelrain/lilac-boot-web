package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;

import java.util.List;

public interface ISearchService {

    List<SubjectCodeDTO> getSubjectCodes();
}
