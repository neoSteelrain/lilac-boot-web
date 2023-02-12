package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.datamodel.view.LicenseBookListDTO;
import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;
import com.steelrain.springboot.lilac.event.LicenseBookSearchEvent;
import com.steelrain.springboot.lilac.event.LicenseSearchEvent;
import com.steelrain.springboot.lilac.event.SubjectBookSearchEvent;
import com.steelrain.springboot.lilac.event.VideoPlayListSearchEvent;
import com.steelrain.springboot.lilac.repository.ISearchRepository;
import com.steelrain.springboot.lilac.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService implements ISearchService{

    private final ISearchRepository m_searchRepository;
    private final VideoRepository m_videoRepository;
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

    public LicenseBookDetailDTO getLicenseBookDetail(String isbn, short region, int detailRegion){
        LicenseBookDetailDTO resultDTO = null;


        return resultDTO;
    }


    /**
     * 키워드를 검색조건으로 하는 영상정보 결과를 가져온다
     * 영상정보에 대한 기능은 VideoService 에서 담당하므로 VideoPlayListSearchEvent를 발행하여 VideoService에 실행을 위임한다
     * @param keyword 검색할 키워드
     * @return 검색된 재생목록
     */
    public VideoPlayListSearchResultDTO searchPlayList(String keyword, int offset, int count) {
        VideoPlayListSearchEvent searchEvent = VideoPlayListSearchEvent.builder()
                .keyword(keyword)
                .offset(offset)
                .count(count)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getSearchResultDTO();
    }

    /**
     * 주제어코드에 해당하는 도서정보를 가져온다
     * 도서정보는 BookService 에서 담당하므로 SubjectBookSearchEvent 를 발행하여 BookService에 실행을 위임한다.
     * @param subjectCode 주제어코드
     * @return 도서정보
     */
    @Override
    public SubjectBookListDTO getSubjectBookList(int subjectCode) {
        SubjectBookSearchEvent searchEvent = SubjectBookSearchEvent.builder()
                .subjectCode(subjectCode)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getSearchResultDTO();
    }

    /**
     * 자격증코드에 해당하는 자격증정보를 가져온다
     * 자격증정보에 대한 기능은 LicenseService 에서 담당하므로 LicenseSearchEvent를 발행하여 LicenseService에 실행을 위임한다
     * @param licenseCode 자격증코드 
     * @return 자격증정보 : 자격증 이름,설명,시험일정
     */
    @Override
    public LicenseDTO getLicenseInfoByCode(int licenseCode) {
        LicenseSearchEvent searchEvent = LicenseSearchEvent.builder()
                .code(licenseCode)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getLicenseDTO();
    }

    /**
     * 키워드에 해당하는 도서정보, 도서관정보를 가져온다
     * 도서정보에 대한 기능은 BookService 에서 담당하므로 LicenseBookSearchEvent를 발행하여 BookService에서 실행을 위임한다
     * @param keyword 검색할 키워드
     * @param region 지역코드 : 예) 서울, 인천, 대구
     * @param detailRegion 세부지역코드 : 예) 인천 미추홀구
     * @return 자격증에 대한 도서정보
     */
    @Override
    public LicenseBookListDTO getLicenseBookList(String keyword, short region, int detailRegion){
        LicenseBookSearchEvent searchEvent = LicenseBookSearchEvent.builder()
                .keyword(keyword)
                .region(region)
                .detailRegion(detailRegion)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getLicenseBookListDTO();
    }
}
