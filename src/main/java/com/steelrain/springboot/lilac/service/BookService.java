package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.config.APIConfig;
import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import com.steelrain.springboot.lilac.repository.IKaKoBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 참고도서 서비스
 */
@Slf4j
@Service
public class BookService implements IBookService{

    private final APIConfig m_apiConfig;
    private final IKaKoBookRepository m_kaKoBookRepository;


    public BookService(APIConfig apiConfig, IKaKoBookRepository kaKoBookRepository){
        m_apiConfig = apiConfig;
        m_kaKoBookRepository = kaKoBookRepository;
    }

    @Override
    public Map<KaKaoBookDTO, List<NaruLibraryDTO>> getKakaoBookLibraryList(String keyword, String region, String detailRegion) {
        log.debug(m_apiConfig.toString());

        return null;
    }

    public String searchLibraryByISBN(String isbn, String region, String detailRegion){
        return null;
    }
}
