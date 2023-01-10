package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.config.APIConfig;
import com.steelrain.springboot.lilac.datamodel.api.KakaoSearchedBookDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruBookExistResposeDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByBookResponseDTO;
import com.steelrain.springboot.lilac.repository.IKaKoBookRepository;
import com.steelrain.springboot.lilac.repository.INaruRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 참고도서 서비스
 */
@Slf4j
@Service
public class BookService implements IBookService{

    private final IKaKoBookRepository m_kaKoBookRepository;
    private final INaruRepository m_naruRepository;


    public BookService(APIConfig apiConfig, IKaKoBookRepository kaKoBookRepository, INaruRepository naruRepository){
        m_kaKoBookRepository = kaKoBookRepository;
        m_naruRepository = naruRepository;
    }

    @Override
    public Map<KakaoSearchedBookDTO, List<NaruLibSearchByBookResponseDTO.Library>> getKakaoBookLibraryList(String keyword, short region, int detailRegion) {
        /*
            1. 카카오 도서검색 API 를 통해 keyword 해당하는 도서목록을 가져온다
            2. 도서의 ISBN을 가지고 나루도서관 API 에 소장도서관목모글 가져온다
            3. 도서를 소장하고 있는 도서관이 있으면 대출이 가능여부를 가져오고 설정해준다
            - 카카오 API는 ISBN을 문자열로 반환하고, 나루도서관 API는 ISBN을 숫자형식으로 받으므로 형변환이 필요하다
         */

        List<KakaoSearchedBookDTO> bookList = m_kaKoBookRepository.searchBookfromKakao(keyword).getKakaoSearchedBookList();
        Map<KakaoSearchedBookDTO, List<NaruLibSearchByBookResponseDTO.Library>> resultMap = new HashMap<>(bookList.size());
        for (KakaoSearchedBookDTO book : bookList) {
            //long isbn = Long.valueOf(book.getIsbn());
            String tmpIsbn = book.getIsbn();
            long isbn = Long.valueOf(StringUtils.containsWhitespace(tmpIsbn) ? StringUtils.tokenizeToStringArray(tmpIsbn, " ")[1] : tmpIsbn);
            NaruLibSearchByBookResponseDTO naruLibResult = m_naruRepository.getLibraryByBook(isbn, region, detailRegion);
            if(naruLibResult.getResponse().getNumfound() <= 0){
                resultMap.put(book,new ArrayList<NaruLibSearchByBookResponseDTO.Library>(0));
                continue;
            }

            resultMap.put(book, getLibraryAndCheckLoanable(isbn, naruLibResult.getResponse().getLibs()));
        }
        return resultMap;
    }


    /**
     * 1. 나루도서관 소장도서관 API 에서 소장도서관 목록을 가져온다.
     * 2. ISBN에 해당하는 도서가 소장가능한지 여부를 가져온다.
     * @param list json 역직렬화 객체
     * @return 소장도서관 목록
     */
    private List<NaruLibSearchByBookResponseDTO.Library> getLibraryAndCheckLoanable(long isbn, List<NaruLibSearchByBookResponseDTO.Libs> list){
        List<NaruLibSearchByBookResponseDTO.Library> resultList = new ArrayList<>(list.size());
        for(NaruLibSearchByBookResponseDTO.Libs lib : list){
            NaruLibSearchByBookResponseDTO.Library tmpLib = lib.getLib();

            // 소장가능여부 가져오기
            NaruBookExistResposeDTO exist = m_naruRepository.checkBookExist(isbn, Integer.valueOf(tmpLib.getLibcode()));
            NaruBookExistResposeDTO.Result res = exist.getResponse().getResult();
            tmpLib.setHasBook(res.getHasbook());
            tmpLib.setLoanAvailable(res.getLoanavailable());

            resultList.add(tmpLib);
        }
        return resultList;
    }
}
