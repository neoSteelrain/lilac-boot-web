package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LibraryDetailRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LibraryRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;
import com.steelrain.springboot.lilac.repository.ISearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService{

    private final ISearchRepository m_searchRepository;

    @Override
    public List<SubjectCodeDTO> getSubjectCodes() {
        return m_searchRepository.getSubjectCodes();
    }

    @Override
    public List<LibraryRegionCodeDTO> getLibRegionCodes() {
        return m_searchRepository.getLibRegionCodes();
    }

    @Override
    public List<LibraryDetailRegionCodeDTO> getLibDetailRegionCodes(int regionCode) {
        return m_searchRepository.getLibDetailRegionCodes(regionCode);
    }
}
