package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseBookDetailDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseBookListDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import com.steelrain.springboot.lilac.datamodel.api.KakaoSearchedBookDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruBookExistResposeDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByBookResponseDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByRegionResponseDTO;
import com.steelrain.springboot.lilac.repository.IKaKoBookRepository;
import com.steelrain.springboot.lilac.repository.INaruRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 참고도서 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookService implements IBookService{

    private final IKaKoBookRepository m_kaKoBookRepository;
    private final INaruRepository m_naruRepository;
    private final ICacheService m_cacheService;



    // 책검색 APi 수정이전 버전, 카카오 책검색과 나루API를 한번에 같이 호출하는 버전
    /*@Override
    public List<LicenseBookDTO> getLicenseBookList(String keyword, short region, int detailRegion){
        *//*
            1. 카카오 도서검색 API 를 통해 keyword 해당하는 도서목록을 가져온다
            2. 도서의 ISBN을 가지고 나루도서관 API 에 소장도서관목록을 가져온다
            3. 도서를 소장하고 있는 도서관이 있으면 대출이 가능여부를 가져오고 설정해준다
            - 카카오 API는 ISBN을 문자열로 반환하고, 나루도서관 API는 ISBN을 숫자형식(long)으로 받으므로 형변환이 필요하다
         *//*
        List<KakaoSearchedBookDTO> bookList = m_kaKoBookRepository.searchBookfromKakao(keyword).getKakaoSearchedBookList();
        List<LicenseBookDTO> resultDTOList = new ArrayList<>(bookList.size());
        for(KakaoSearchedBookDTO book : bookList){
            *//*
             - 카카오 API에서 ISBN을 "ISBN10 ISBN13" (예: "1160503141 9791160503142" ) 2가지 ISBN을 넘겨주는 경우가 있으므로 2가지 같이넘어올 경우 ISBN13을 선택해서 사용한다
             - ISBN 문자열을 자르는 작업이 필요하다
             *//*
            String tmpIsbn = book.getIsbn();
            String splitedIsbn = StringUtils.containsWhitespace(tmpIsbn) ? StringUtils.tokenizeToStringArray(tmpIsbn, " ")[1] : tmpIsbn;
            long isbn = Long.valueOf(splitedIsbn);
            NaruLibSearchByBookResponseDTO naruLibResult = m_naruRepository.getLibraryByBook(isbn, region, detailRegion);
            LicenseBookDTO resultDTO = new LicenseBookDTO();
            resultDTO.setKakaoBookDTO(convertKakaoBookDTO(book, splitedIsbn)); // 한번자른 ISBN문자열을 다시 자르는 일이 없도록 잘라진 ISBN을 같이 넘겨준다.
            resultDTO.setLibraryList(convertNaruLibraryDTO(naruLibResult, resultDTO.getKakaoBookDTO().getIsbn13()));
            resultDTO.setKeyword(keyword);
            resultDTO.setRegionName(m_cacheService.getRegionName(region));
            resultDTO.setDetailRegionName(m_cacheService.getDetailRegionName(region, detailRegion));
            resultDTOList.add(resultDTO);
        }
        return resultDTOList;
    }*/

    // 카카오 책검색만 호출하는 버전
    @Override
    public LicenseBookListDTO getLicenseBookList(String keyword, short region, int detailRegion){
        /*
            1. 카카오 도서검색 API 를 통해 keyword 해당하는 도서목록을 가져온다
            2. 도서의 ISBN을 가지고 나루도서관 API 에 소장도서관목록을 가져온다
            3. 도서를 소장하고 있는 도서관이 있으면 대출이 가능여부를 가져오고 설정해준다
            - 카카오 API는 ISBN을 문자열로 반환하고, 나루도서관 API는 ISBN을 숫자형식(long)으로 받으므로 형변환이 필요하다
         */
        List<KakaoSearchedBookDTO> bookList = m_kaKoBookRepository.searchBookfromKakao(keyword).getKakaoSearchedBookList();
        LicenseBookListDTO resultDTO = new LicenseBookListDTO();
        List<KaKaoBookDTO> kaKaoBookDTOList = new ArrayList<>(bookList.size());
        for(KakaoSearchedBookDTO book : bookList){
            if(book.getPrice() <= 0 || book.getSalePrice() <= 0){
                continue;
            }
            String tmpIsbn = book.getIsbn();
            kaKaoBookDTOList.add(convertKakaoBookDTO(book,
                                 StringUtils.containsWhitespace(tmpIsbn) ? StringUtils.tokenizeToStringArray(tmpIsbn, " ")[1] : tmpIsbn));

            resultDTO.setKeyword(keyword);
            resultDTO.setRegionName(m_cacheService.getRegionName(region));
            resultDTO.setDetailRegionName(m_cacheService.getDetailRegionName(region, detailRegion));
        }
        resultDTO.setKakaoBookList(kaKaoBookDTOList);
        resultDTO.setLibraryList(delegateLibraryByRegionList(region, detailRegion));
        return resultDTO;
    }

    public LicenseBookDetailDTO getLicenseBookDetail(String isbn, short region, int detailRegion){
        LicenseBookDetailDTO resultDTO = null;

        return resultDTO;
    }

    @Override
    public List<NaruLibraryDTO> getLibraryByRegionList(short region, int detailRegion) {
        return delegateLibraryByRegionList(region, detailRegion);
    }

    private List<NaruLibraryDTO> delegateLibraryByRegionList(short region, int detailRegion){
        NaruLibSearchByRegionResponseDTO naruLibResult = m_naruRepository.getLibraryByRegion(region, detailRegion);
        return convertNaruLibraryDTO(naruLibResult);
    }

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

    private List<NaruLibraryDTO> convertNaruLibraryDTO(NaruLibSearchByBookResponseDTO srcNaruDTO, String isbn13){
        if(srcNaruDTO.getResponse().getNumfound() <= 0){
            return new ArrayList<>(0);
        }
        List<NaruLibraryDTO> resultDTOList = new ArrayList<>(srcNaruDTO.getResponse().getNumfound());
        for(NaruLibSearchByBookResponseDTO.Libs lib : srcNaruDTO.getResponse().getLibs()){
            NaruLibSearchByBookResponseDTO.Library library = lib.getLib();

            // 소장가능여부 가져오기
            NaruBookExistResposeDTO existDTO = m_naruRepository.checkBookExist(Long.valueOf(isbn13), Integer.valueOf(library.getLibcode()));
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
                            .isbn13(isbn13)
                            .hasBook("Y".equals(existResult.getHasbook()))
                            .isLoanAvailable("Y".equals(existResult.getLoanavailable()))
                            .build());
        }
        return resultDTOList;
    }

    private KaKaoBookDTO convertKakaoBookDTO(KakaoSearchedBookDTO srcBook, String splitedIsbn){
        return KaKaoBookDTO.builder()
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
