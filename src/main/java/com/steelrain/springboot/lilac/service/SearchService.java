package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.event.LicenseBookSearchEvent;
import com.steelrain.springboot.lilac.event.LicenseSearchEvent;
import com.steelrain.springboot.lilac.repository.ISearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService{

    private final ISearchRepository m_searchRepository;
    private final ApplicationEventPublisher m_appEventPublisher;

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

    @Override
    public LicenseDTO getLicenseInfoByCode(int licenseCode) {
        LicenseSearchEvent searchEvent = LicenseSearchEvent.builder()
                .code(licenseCode)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getLicenseDTO();
    }

    @Override
    public List<LicenseBookDTO> getLicenseBookList(String keyword, short region, int detailRegion){
        LicenseBookSearchEvent searchEvent = LicenseBookSearchEvent.builder()
                .keyword(keyword)
                .region(region)
                .detailRegion(detailRegion)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getLicenseBookDTOList();
    }
}
