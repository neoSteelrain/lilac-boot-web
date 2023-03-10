package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.ICacheService;
import com.steelrain.springboot.lilac.common.PagingUtils;
import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.api.*;
import com.steelrain.springboot.lilac.datamodel.view.*;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import com.steelrain.springboot.lilac.event.*;
import com.steelrain.springboot.lilac.repository.BookRepository;
import com.steelrain.springboot.lilac.repository.IKaKoBookRepository;
import com.steelrain.springboot.lilac.repository.INaruRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 참고도서 서비스
 * - 도서에 대한 비즈니스 로직을 처리한다
 * - 도서와 관련된 이벤트를 처리/발행 한다
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookService implements IBookService{

    private final IKaKoBookRepository m_kaKoBookRepository;
    private final INaruRepository m_naruRepository;
    private final ICacheService m_cacheService;
    private final BookRepository m_bookRepository;
    private final ApplicationEventPublisher m_applicationEventPublisher;


    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public LicenseBookListDTO getLicenseBookList(int licenseCode, short regionCode, int detailRegionCode, int pageNum, int bookCount){
        /*
            1. 카카오 도서검색 API 를 통해 keyword 해당하는 도서목록을 가져온다
            2. 도서의 ISBN을 가지고 나루도서관 API 에 소장도서관목록을 가져온다
            - 카카오 API는 ISBN을 문자열로 반환하고, 나루도서관 API는 ISBN을 숫자형식(long)으로 받으므로 형변환이 필요하다
         */
        String licenseSearchKeyword = m_cacheService.getLicenseKeyword(licenseCode);
        KakaoBookSearchResponseDTO responseDTO = m_kaKoBookRepository.searchBookFromKakao(licenseSearchKeyword, pageNum, bookCount);
        List<KakaoSearchedBookDTO> bookList = responseDTO.getKakaoSearchedBookList();
        LicenseBookListDTO resultDTO = new LicenseBookListDTO();
        List<KaKaoBookDTO> kaKaoBookDTOList = new ArrayList<>(bookList.size());
        for(KakaoSearchedBookDTO book : bookList){
            if(book.getPrice() <= -1 || book.getSalePrice() <= -1){ // 간혹 책가격이 -1로 설정된 책이 있으므로 빼주어야 한다. 판매중지로 설정해줘야 할지 고민이다.
                continue;
            }
            String tmpIsbn = book.getIsbn();
            kaKaoBookDTOList.add(convertKakaoBookDTO(book,
                                 StringUtils.containsWhitespace(tmpIsbn.trim()) ? StringUtils.tokenizeToStringArray(tmpIsbn, " ")[1] : tmpIsbn));

            resultDTO.setKeyword(licenseSearchKeyword);
            resultDTO.setRegionName(m_cacheService.getRegionName(regionCode));
            if(detailRegionCode > 0){
                resultDTO.setDetailRegionName(m_cacheService.getDetailRegionName(regionCode, detailRegionCode));
            }
        }
        // 카카오책 검색결과를 DB에 저장하는 이벤트를 발생한다.
        publishKaKaoBookSaveEvent(kaKaoBookDTOList);

        resultDTO.setLicenseCode(licenseCode);
        resultDTO.setRegionCode(regionCode);
        resultDTO.setDetailRegionCode(detailRegionCode);
        resultDTO.setDetailRegionName(m_cacheService.getRegionName(regionCode));
        resultDTO.setDetailRegionName(m_cacheService.getDetailRegionName(regionCode, detailRegionCode));
        resultDTO.setTotalBookCount(responseDTO.getMeta().getTotalCount());
        resultDTO.setKakaoBookList(kaKaoBookDTOList);
        resultDTO.setLibraryList(delegateLibraryByRegionList(regionCode, detailRegionCode));
        resultDTO.setPageInfo(PagingUtils.createPagingInfo(responseDTO.getMeta().getTotalCount(), pageNum, bookCount));
        return resultDTO;
    }

    @Override
    public List<NaruLibraryDTO> getLibraryByRegionList(short region, int detailRegion) {
        return delegateLibraryByRegionList(region, detailRegion);
    }

    @Override
    public SubjectBookListDTO getSubjectBookList(int subjectCode, int pageNum, int bookCount) {
        String keyword = m_cacheService.getSubjectKeywordBook(subjectCode);
        SubjectBookListDTO resultDTO = getBookListByKeyword(keyword, pageNum, bookCount);
        resultDTO.setSubjectCode(subjectCode);
        resultDTO.setSubjectName(m_cacheService.getSubjectName(subjectCode));
        return resultDTO;
    }

    private SubjectBookListDTO getBookListByKeyword(String keyword, int pageNum, int bookCount){
        KakaoBookSearchResponseDTO responseDTO = m_kaKoBookRepository.searchBookFromKakao(keyword, pageNum, bookCount);
        SubjectBookListDTO resultDTO = new SubjectBookListDTO();
        List<KakaoSearchedBookDTO> bookList = responseDTO.getKakaoSearchedBookList();
        List<KaKaoBookDTO> kaKaoBookDTOList = new ArrayList<>(bookList.size());
        for (KakaoSearchedBookDTO book : bookList) {
            if(book.getPrice() <= -1 || book.getSalePrice() <= -1){ // 간혹 책가격이 -1로 설정된 책이 있으므로 빼주어야 한다.
                continue;
            } // ISBN이 하나 오는 경우 앞에 공백이 붙어서 오는 경우가 있으므로, ISBN 문자열에 trim을 해준다.
            log.debug(String.format("===== > 카카오책 검색의 책1권의 KakaoSearchedBookDTO 정보 : %s", book.toString()));
            String tmpIsbn = book.getIsbn();
            kaKaoBookDTOList.add(convertKakaoBookDTO(book,
                    StringUtils.containsWhitespace(tmpIsbn.trim()) ? StringUtils.tokenizeToStringArray(tmpIsbn, " ")[1] : tmpIsbn));
        }
        resultDTO.setKeyword(keyword);
        resultDTO.setKakaoBookList(kaKaoBookDTOList);
        resultDTO.setTotalBookCount(responseDTO.getMeta().getTotalCount());
        // 카카오책 검색결과를 DB에 저장하는 이벤트를 발생한다.
        publishKaKaoBookSaveEvent(kaKaoBookDTOList);
        resultDTO.setPageInfo(PagingUtils.createPagingInfo(responseDTO.getMeta().getTotalCount(), pageNum, bookCount));
        return resultDTO;
    }


    @Override
    public BookDetailDTO getBookDetailInfo(Long isbn, short regionCode, int detailRegionCode) {
        /*
            도서정보와 도서를 소장하고 있는 도서관의 정보를 찾아서 반환한다.
         */
        KaKaoBookDTO bookDTO = m_bookRepository.findKaKaoBookInfo(isbn);
        List<NaruLibraryDTO> libList = null;
        if(regionCode == 0 || (regionCode == 0 && detailRegionCode == 0)){
            libList = new ArrayList<>(0);
        }else{
            NaruLibSearchByBookResponseDTO libSearchResponse = m_naruRepository.getLibraryByBook(isbn, regionCode, detailRegionCode);
            libList = convertNaruLibraryDTO(libSearchResponse, Long.valueOf(bookDTO.getIsbn13()));
        }
        return BookDetailDTO.builder()
                .bookDTO(bookDTO)
                .libraryList(libList)
                .build();
    }

    @Override
    public RecommendedBookListDTO getRecommendedBookList() {
        RecommendedBookListDTO result = new RecommendedBookListDTO();
        result.setKakaoBookList(m_bookRepository.getRecommendedBookList());
        return result;
    }

    @EventListener(KeywordBookSearchEvent.class)
    public void handleKeywordBookSearchEvent(KeywordBookSearchEvent event){
        SubjectBookListDTO sbjBookListDTO = getBookListByKeyword(event.getKeyword(), event.getPageNum(), event.getBookCount());
        sbjBookListDTO.setSubjectName(event.getKeyword());
        event.setKeywordBookListDTO(sbjBookListDTO);
    }

    @EventListener(LicenseBookSearchEvent.class)
    public void handleLicenseBookSearchEvent(LicenseBookSearchEvent event){
        event.setLicenseBookListDTO(getLicenseBookList(event.getLicenseCode(), event.getRegionCode(), event.getDetailRegionCode(), event.getPageNum(), event.getLicenseBookCount()));
    }

    @EventListener(SubjectBookSearchEvent.class)
    public void handleSubjectBookSearchEvent(SubjectBookSearchEvent event){
        event.setSearchResultDTO(getSubjectBookList(event.getSubjectCode(), event.getPageNum(), event.getSubjectBookCount()));
    }

    @EventListener(BookDetailSearchEvent.class)
    public void handleBookDetailSearchEvent(BookDetailSearchEvent event){
        event.setBookDetailInfo(getBookDetailInfo(event.getIsbn(), event.getRegionCode(), event.getDetailRegionCode()));
    }

    @Async
    @EventListener(KakaoBookSaveEvent.class)
    public void handleBookSaveEvent(KakaoBookSaveEvent event){
        m_bookRepository.saveKakaoBookList(event.getKaKaoBookList());
    }

    // 카카오 API로 검색된 결과를 DB에 저장하려고 할때 사용한다.
    private void publishKaKaoBookSaveEvent(List<KaKaoBookDTO> bookList){
        if(bookList == null || bookList.size() == 0){
            return;
        }
        KakaoBookSaveEvent bookSaveEvent = KakaoBookSaveEvent.builder()
                .kaKaoBookList(bookList)
                .build();
        m_applicationEventPublisher.publishEvent(bookSaveEvent);
    }

    /*
        실제로 naru API를 호출해서 결과를 반환하는 helper 메서드
     */
    private List<NaruLibraryDTO> delegateLibraryByRegionList(short region, int detailRegion){
        NaruLibSearchByRegionResponseDTO naruLibResult = m_naruRepository.getLibraryByRegion(region, detailRegion);
        return convertNaruLibraryDTO(naruLibResult);
    }

    /*
        나루 도서관검색 API의 결과를 NaruLibraryDTO로 변환시켜준다
     */
    private List<NaruLibraryDTO> convertNaruLibraryDTO(NaruLibSearchByRegionResponseDTO srcNaruDTO){
        List<NaruLibraryDTO> resultDTOList = new ArrayList<>(srcNaruDTO.getResponse().getNumfound());
        for(NaruLibSearchByRegionResponseDTO.Libs lib : srcNaruDTO.getResponse().getLibs()){
            NaruLibSearchByRegionResponseDTO.Library library = lib.getLib();
            resultDTOList.add(NaruLibraryDTO.builder()
                    .libCode(library.getLibcode())
                    .name(library.getLibname())
                    .address(library.getAddress())
                    .tel(library.getTel())
                    .fax(library.getFax())
                    .latitude(library.getLatitude())
                    .longitude(library.getLongitude())
                    .homepage(library.getHomepage())
                    .closed(library.getClosed())
                    .operatingTime(library.getOperatingtime())
                    .build());
        }
        return resultDTOList;
    }

    // 도서 1권에 대해 소장하고 있는 도서관 목록을 반환하는 메서드 : 로직변경으로 일단 주석처리
    private List<NaruLibraryDTO> convertNaruLibraryDTO(NaruLibSearchByBookResponseDTO srcNaruDTO, Long isbn13){
        if(Objects.isNull(srcNaruDTO)){
            return new ArrayList<>(0);
        }
        if(srcNaruDTO.getResponse().getNumfound() <= 0){
            return new ArrayList<>(0);
        }
        List<NaruLibraryDTO> resultDTOList = new ArrayList<>(srcNaruDTO.getResponse().getNumfound());
        for(NaruLibSearchByBookResponseDTO.Libs lib : srcNaruDTO.getResponse().getLibs()){
            NaruLibSearchByBookResponseDTO.Library library = lib.getLib();

            // 소장가능여부 가져오기
            NaruBookExistResposeDTO existDTO = m_naruRepository.checkBookExist(isbn13, Integer.valueOf(library.getLibcode()));
            NaruBookExistResposeDTO.Result existResult = existDTO.getResponse().getResult();
            resultDTOList.add(NaruLibraryDTO.builder()
                            .libCode(library.getLibcode())
                            .name(library.getLibname())
                            .address(library.getAddress())
                            .tel(library.getTel())
                            .latitude(library.getLatitude())
                            .longitude(library.getLongitude())
                            .homepage(library.getHomepage())
                            .closed(library.getClosed())
                            .operatingTime(library.getOperatingtime())
//                            .isbn13(isbn13)
                            .hasBook("Y".equals(existResult.getHasbook()))
                            .isLoanAvailable("Y".equals(existResult.getLoanavailable()))
                            .build());
        }
        return resultDTOList;
    }

    private KaKaoBookDTO convertKakaoBookDTO(KakaoSearchedBookDTO srcBook, String splitedIsbn){
        return KaKaoBookDTO.builder()
                .isbn13Long(Long.valueOf(splitedIsbn.trim()))
                .isbn13(splitedIsbn)
                .title(srcBook.getTitle())
                .contents(srcBook.getContents())
                .url(srcBook.getUrl())
                .publishDate(Timestamp.valueOf(ZonedDateTime.parse(srcBook.getDatetime()).toLocalDateTime()))
                .authors(String.join(",",srcBook.getAuthors()))
                .publisher(srcBook.getPublisher())
                .translators(String.join(",",srcBook.getTranslators()))
                .price(srcBook.getPrice())
                .salePrice(srcBook.getSalePrice())
                .thumbnail(srcBook.getThumbnail())
                .status(srcBook.getStatus())
                .build();
    }
}
