package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 검색이벤트에 대한 이벤트리스너 클래스
 * 이벤트 처리는 Service 클래스에 맏기며 동기식으로 동작한다
 * 처리결과는 event 전달인자에 설정하여 이벤트 발행자가 결과를 받아볼 수 있게 한다
 */
@Component
@RequiredArgsConstructor
public class SearchEventHandler {

    private final ILicenseService m_licenseService;
    private final IBookService m_bookService;
    private final IVideoService m_videoService;


    @EventListener(LicenseSearchEvent.class)
    public void handleLicenseSearchEvent(LicenseSearchEvent event){
        event.setLicenseDTO(m_licenseService.getLicenseSchedulesByCode(event.getCode()));
    }

    @EventListener(LicenseBookSearchEvent.class)
    public void handleLicenseBookSearchEvent(LicenseBookSearchEvent event){
        event.setLicenseBookListDTO(m_bookService.getLicenseBookList(event.getLicenseCode(), event.getRegionCode(), event.getDetailRegionCode(), event.getPageNum(), event.getLicenseBookCount()));
    }

    @EventListener(VideoPlayListSearchEvent.class)
    public void handleVideoPlayListSearchEvent(VideoPlayListSearchEvent event){
        event.setSearchResultDTO(m_videoService.searchPlayList(event.getKeywordCode(), event.getKeyword(), event.getPageNum(), event.getPlaylistCount(), event.getKeywordType()));
    }

    @EventListener(SubjectBookSearchEvent.class)
    public void handleSubjectBookSearchEvent(SubjectBookSearchEvent event){
        event.setSearchResultDTO(m_bookService.getSubjectBookList(event.getSubjectCode(), event.getPageNum(), event.getSubjectBookCount()));
    }

    @EventListener(BookDetailSearchEvent.class)
    public void handleBookDetailSearchEvent(BookDetailSearchEvent event){
        event.setBookDetailInfo(m_bookService.getBookDetailInfo(event.getIsbn(), event.getRegionCode(), event.getDetailRegionCode()));
    }
}
