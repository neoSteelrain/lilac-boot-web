package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.datamodel.view.BookDetailDTO;
import com.steelrain.springboot.lilac.datamodel.view.LicenseBookListDTO;
import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;
import com.steelrain.springboot.lilac.event.*;
import com.steelrain.springboot.lilac.repository.ISearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 검색 서비스
 * - 검색의 비즈니스 로직을 구현
 * - 검색관련 이벤트를 처리/발행 한다
 */
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


    /**
     * 키워드코드를 검색조건으로 하는 영상정보 결과를 가져온다
     * 영상정보에 대한 기능은 VideoService 에서 담당하므로 VideoPlayListSearchEvent를 발행하여 VideoService에 실행을 위임한다
     * @param keywordCode 검색할 키워드의 코드
     * @param pageNum 페이지 번호
     * @param playlistCount 페이지당 재생목록의 개수
     * @param keywordType 키워드 종류
     * @return 검색된 재생목록
     */
    @Override
    public VideoPlayListSearchResultDTO searchPlayList(int keywordCode, String searchKeyword, int pageNum, int playlistCount, KEYWORD_TYPE keywordType) {
        VideoPlayListSearchEvent searchEvent = VideoPlayListSearchEvent.builder()
                .keywordCode(keywordCode)
                .keyword(searchKeyword)
                .pageNum(pageNum)
                .playlistCount(playlistCount)
                .keywordType(keywordType)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getSearchResultDTO();
    }

    public VideoPlayListSearchResultDTO searchPlayList(String searchKeyword, int pageNum, int playlistCount){
        VideoPlayListSearchEvent searchEvent = VideoPlayListSearchEvent.builder()
                .keyword(searchKeyword)
                .pageNum(pageNum)
                .playlistCount(playlistCount)
                .keywordType(KEYWORD_TYPE.KEYWORD)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getSearchResultDTO();
    }

    /**
     * 주제코드에 해당하는 도서정보를 가져온다
     * 도서정보는 BookService 에서 담당하므로 SubjectBookSearchEvent 를 발행하여 BookService에 실행을 위임한다.
     * @param subjectCode 주제코드
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
     * 도서의 자세한정보를 반환한다
     * 도서정보는 BookService 에서 담당하므로 BookDetailSearchEvent 를 발행하여 BookService에 실행을 위임한다.
     * @param isbn 도서의 ISBN
     * @param regionCode 지역코드
     * @param detailRegionCode 세부지역코드
     * @return 도서의 세부정보
     */
    @Override
    public BookDetailDTO getBookDetailInfo(Long isbn, short regionCode, int detailRegionCode) {
        BookDetailSearchEvent searchEvent = BookDetailSearchEvent.builder()
                .isbn(isbn)
                .regionCode(regionCode)
                .detailRegionCode(detailRegionCode)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getBookDetailInfo();
    }

    @Override
    public SubjectBookListDTO getSubjectBookList(String keyword, int pageNum, int bookCount) {
        KeywordBookSearchEvent searchEvent = KeywordBookSearchEvent.builder()
                .keyword(keyword)
//                .regionCode(regionCode)
//                .detailRegionCode(detailRegionCode)
                .pageNum(pageNum)
                .bookCount(bookCount)
                .build();
        m_appEventPublisher.publishEvent(searchEvent);
        return searchEvent.getKeywordBookListDTO();
    }

    /**
     * 자격증코드에 해당하는 자격증정보를 가져온다
     * 자격증정보에 대한 기능은 LicenseService 에서 담당하므로 LicenseSearchEvent를 발행하여 LicenseService에 실행을 위임한다
     * @param licenseCode 자격증코드 
     * @return 자격증정보 : 자격증 이름,설명,시험일정
     */
    @Override
    public LicenseDTO getLicenseInfoByCode(int licenseCode) {
        if(licenseCode > 0){
            LicenseSearchEvent searchEvent = LicenseSearchEvent.builder()
                    .code(licenseCode)
                    .build();
            m_appEventPublisher.publishEvent(searchEvent);
            return searchEvent.getLicenseDTO();
        }
        LicenseDTO result = new LicenseDTO();
        result.setLicenseName("자격증 정보가 없습니다");
        result.setScheduleList(new ArrayList<>(0));
        return result;
    }

    /**
     * 자격증코드에 해당하는 도서정보, 도서관정보를 가져온다
     * 도서정보에 대한 기능은 BookService 에서 담당하므로 LicenseBookSearchEvent를 발행하여 BookService에서 실행을 위임한다
     * 화면에 보여질 도서의 갯수를 이벤트에 지정해주고 이벤트를 발행한다.
     * @param licenseCode 자격증코드
     * @param regionCode 지역코드
     * @param detailRegionCode 세부지역코드
     * @param pageNum 페이지번호
     * @param bookCount 페이지에 보여질 책의 권수
     * @return 자격증, 도서관 정보
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
