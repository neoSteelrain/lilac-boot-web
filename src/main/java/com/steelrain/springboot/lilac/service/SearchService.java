package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.BOOK_PAGING_INFO;
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
     * @param keywordCode 검색할 키워드의 코드
     * @param pageNum 페이지 번호
     * @param playlistCount 페이지당 재생목록의 개수
     * @param keywordType 키워드 종류
     * @return 검색된 재생목록
     */
    @Override
    public VideoPlayListSearchResultDTO searchPlayList(int keywordCode, int pageNum, int playlistCount, int keywordType) {
        VideoPlayListSearchEvent searchEvent = VideoPlayListSearchEvent.builder()
                .keywordCode(keywordCode)
                .pageNum(pageNum)
                .playlistCount(playlistCount)
                .keywordType(keywordType)
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
    public SubjectBookListDTO getSubjectBookList(int subjectCode, int pageNum, int bookCount) {
        SubjectBookSearchEvent searchEvent = SubjectBookSearchEvent.builder()
                .subjectCode(subjectCode)
                .pageNum(pageNum)
                .subjectBookCount(bookCount)
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
     * 화면에 보여질 도서의 갯수를 이벤트에 지정해주고 이벤트를 발행한다.
     * @param licenseCode 자격증코드
     * @param regionCode 지역코드
     * @param detailRegionCode 세부지역코드
     * @return 검색된 자격증관련 도서목록
     */
    @Override
    public LicenseBookListDTO getLicenseBookList(int licenseCode, short regionCode, int detailRegionCode, int pageNum, int bookCount){
        LicenseBookSearchEvent searchEvent = LicenseBookSearchEvent.builder()
                .licenseCode(licenseCode)
                .regionCode(regionCode)
                .detailRegionCode(detailRegionCode)
                .pageNum(pageNum)
                .licenseBookCount(bookCount)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getLicenseBookListDTO();
    }
}
