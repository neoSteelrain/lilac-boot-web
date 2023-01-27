package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;
import com.steelrain.springboot.lilac.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchRepository implements ISearchRepository{

    private final SearchMapper m_searchMapper;

    @Override
    public List<SubjectCodeDTO> getSubjectCodes() {
        return m_searchMapper.getSubjectCodes();
    }
}
