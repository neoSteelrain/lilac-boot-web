package com.steelrain.springboot.lilac.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steelrain.springboot.lilac.common.ICacheService;
import com.steelrain.springboot.lilac.common.PagingUtils;
import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.api.*;
import com.steelrain.springboot.lilac.datamodel.view.*;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import com.steelrain.springboot.lilac.event.*;
import com.steelrain.springboot.lilac.exception.LilacServiceException;
import com.steelrain.springboot.lilac.exception.NaruAPIQuotaOverException;
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
import java.util.*;

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


    @Override
    @Transactional
    public LicenseBookListDTO getLicenseBookList(int licenseCode, short regionCode, int detailRegionCode, int pageNum, int bookCount) throws NaruAPIQuotaOverException{
        /*
            1. 카카오 도서검색 API 를 통해 keyword 해당하는 도서목록을 가져온다
            2. 도서의 ISBN을 가지고 나루도서관 API 에 소장도서관목록을 가져온다
            - 카카오 API는 ISBN을 문자열로 반환하고, 나루도서관 API는 ISBN을 숫자형식(long)으로 받으므로 형변환이 필요하다
         */
        String licenseSearchKeyword = m_cacheService.getLicenseKeyword(licenseCode);
        KakaoBookSearchResponseDTO responseDTO = m_kaKoBookRepository.searchBookFromKakao(licenseSearchKeyword, pageNum, bookCount);
        List<KakaoSearchedBookDTO> responseBookList = responseDTO.getKakaoSearchedBookList();
        LicenseBookListDTO resultDTO = new LicenseBookListDTO();
        List<KaKaoBookDTO> kaKaoBookList = new ArrayList<>(responseBookList.size());
        for(KakaoSearchedBookDTO book : responseBookList){
            if(book.getPrice() <= -1 || book.getSalePrice() <= -1){ // 간혹 책가격이 -1로 설정된 책이 있으므로 빼주어야 한다. 판매중지로 설정해줘야 할지 고민이다.
                continue;
            }
            String tmpIsbn = book.getIsbn();
            kaKaoBookList.add(convertKakaoBookDTO(book,
                                 StringUtils.containsWhitespace(tmpIsbn.trim()) ? StringUtils.tokenizeToStringArray(tmpIsbn, " ")[1] : tmpIsbn, licenseCode, 0));
        }

        // 카카오책 검색결과를 DB에 저장하는 이벤트를 발생한다.
        publishKaKaoBookSaveEvent(kaKaoBookList);

        resultDTO.setKeyword(licenseSearchKeyword);
        resultDTO.setLicenseCode(licenseCode);
        resultDTO.setRegionCode(regionCode);
        resultDTO.setDetailRegionCode(detailRegionCode);
        resultDTO.setRegionName(m_cacheService.getRegionName(regionCode));

        if(detailRegionCode > 0){
            resultDTO.setDetailRegionName(m_cacheService.getDetailRegionName(regionCode, detailRegionCode));
        }

        resultDTO.setTotalBookCount(responseDTO.getMeta().getTotalCount());
        resultDTO.setKakaoBookList(kaKaoBookList);

        List<NaruLibraryDTO> libList = findLibraryByRegionList(regionCode, detailRegionCode);
        resultDTO.setLibraryList(libList);
        resultDTO.setPageInfo(PagingUtils.createPagingInfo(responseDTO.getMeta().getTotalCount(), pageNum, bookCount));
        
        // 소장도서 API 호출에 너무 많은 시간이 걸려서 관내도서관목록을 출력하는 것으로 대체
        //resultDTO.setCatalogueMap(analyzeCatalogue(kaKaoBookList, libList));

        return resultDTO;
    }

    /*
        bookList 에 있는 책이 libList 도서관에서 소장중인 인지 검사하고, 소장하고 있다면 도서관과 책목록을 연관시켜 준다
     */
    private Map<String, List<KaKaoBookDTO>> analyzeCatalogue(final List<KaKaoBookDTO> bookList, final List<NaruLibraryDTO> libList) {
        ObjectMapper om = new ObjectMapper();
        Map<String, List<KaKaoBookDTO>> catalMap = new HashMap<>(libList.size());
        for(NaruLibraryDTO lib : libList){
            List<KaKaoBookDTO> existBookList = new ArrayList<>(4);
            for(KaKaoBookDTO book : bookList){
                NaruBookExistResposeDTO existInfo = m_naruRepository.checkBookExist(book.getIsbn13Long(), Integer.parseInt(lib.getLibCode()));
                try{
                    String tmp = om.writeValueAsString(existInfo);
                    log.debug("existInfo json 문자열 : {}", tmp);
                }catch(JsonProcessingException ex){
                    log.error("existInfo 에러 : {}", ex);
                    throw new LilacServiceException("도서관 소장여부 확인 중 예외발생 : 예외정보 - {}", ex);
                }

                if("Y".equals(existInfo.getResponse().getResult().getHasbook())){
                    existBookList.add(book);
                }
            }
            catalMap.put(lib.getName(), existBookList);
        }
        return catalMap;
    }

    @Override
    public List<NaruLibraryDTO> getLibraryByRegionList(short region, int detailRegion) throws NaruAPIQuotaOverException {
        return findLibraryByRegionList(region, detailRegion);
    }

    @Override
    public SubjectBookListDTO getSubjectBookList(int subjectCode, int pageNum, int bookCount) {
        String keyword = m_cacheService.getSubjectKeywordBook(subjectCode);
        SubjectBookListDTO resultDTO = getBookListByKeyword(subjectCode, keyword, pageNum, bookCount);
        resultDTO.setSubjectCode(subjectCode);
        resultDTO.setSubjectName(m_cacheService.getSubjectName(subjectCode));
        return resultDTO;
    }

    // 주제키워드 도서 검색을 처리한다,subjectCode는 주제키워드로 검색할때는 0 이상, 키워드로 검색할때는 0 이다
    private SubjectBookListDTO getBookListByKeyword(int subjectCode, String keyword, int pageNum, int bookCount){
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
                    StringUtils.containsWhitespace(tmpIsbn.trim()) ? StringUtils.tokenizeToStringArray(tmpIsbn, " ")[1] : tmpIsbn, 0, subjectCode));
        }
        resultDTO.setKeyword(keyword);
        resultDTO.setKakaoBookList(kaKaoBookDTOList);
        resultDTO.setTotalBookCount(responseDTO.getMeta().getTotalCount());
        // 카카오책 검색결과를 DB에 저장하는 이벤트를 발생한다.
        publishKaKaoBookSaveEvent(kaKaoBookDTOList);

        resultDTO.setPageInfo(PagingUtils.createPagingInfo(responseDTO.getMeta().getTotalCount(), pageNum, bookCount));
        return resultDTO;
    }

    /**
     * 도서정보와 도서를 소장하고 있는 도서관의 정보를 찾아서 반환한다.
     * @param isbn 찾고자하는 도서의 ISBN 
     * @param regionCode 도서관의 지역코드
     * @param detailRegionCode 도서관의 세부지역코드
     * @return 도서정보와 소장도서관목록
     */
    @Override
    public BookDetailDTO getBookDetailInfo(Long isbn, short regionCode, int detailRegionCode) {
        KaKaoBookDTO bookDTO = m_bookRepository.findKaKaoBookInfo(isbn);
        List<NaruLibraryDTO> libList = null;
        if(regionCode <= 0 && detailRegionCode <= 0){
            return BookDetailDTO.builder()
                    .bookDTO(bookDTO)
                    .libraryList(new ArrayList<>(0))
                    .build();
        }
        short leastRegionCode = m_cacheService.getLeastRegionCode();
        int leastDetailRegionCode = m_cacheService.getLeastDetailRegionCode(regionCode);
        if(regionCode < leastRegionCode && detailRegionCode < leastDetailRegionCode){
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
        SubjectBookListDTO sbjBookListDTO = getBookListByKeyword(0, event.getKeyword(), event.getPageNum(), event.getBookCount());
        sbjBookListDTO.setSubjectName(event.getKeyword());
        event.setKeywordBookListDTO(sbjBookListDTO);
    }

    @EventListener(LicenseBookSearchEvent.class)
    public void handleLicenseBookSearchEvent(LicenseBookSearchEvent event) throws NaruAPIQuotaOverException{
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

    /*
       카카오 API로 검색된 결과를 DB에 저장하려고 할때 사용한다
       - 이벤트로 구현한 이유
       1. 검색된 도서를 DB에 저장하는 기능은 별도의 스레드로 분리하기 쉽게 하지 위해 비동기 이벤트로 처리한다
       2. 스스로 이벤트를 발행하고 스스로 이벤트를 비동기로 처리한다
     */
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
    private List<NaruLibraryDTO> findLibraryByRegionList(short region, int detailRegion) throws NaruAPIQuotaOverException {
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

    // 도서 1권에 대해 소장하고 있는 도서관 목록을 반환하는 메서드
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
            NaruBookExistResposeDTO existDTO = m_naruRepository.checkBookExist(isbn13, Integer.parseInt(library.getLibcode()));
            NaruBookExistResposeDTO.Result existResult = existDTO.getResponse().getResult();
            resultDTOList.add(NaruLibraryDTO.builder()
                            .libCode(library.getLibcode())
                            .name(library.getLibname())
                            .address(library.getAddress())
                            .fax(library.getFax())
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

    private KaKaoBookDTO convertKakaoBookDTO(KakaoSearchedBookDTO srcBook, String splitedIsbn, int licenseCode, int subjectCode){
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
                .licenseId(licenseCode > 0 ? m_cacheService.getLicenseIdByCode(licenseCode) : null)
                .subjectId(subjectCode > 0 ? m_cacheService.getSubjectIdByCode(subjectCode) : null)
                .build();
    }
}
