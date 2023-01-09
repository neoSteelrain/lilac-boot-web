package com.steelrain.springboot.lilac.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steelrain.springboot.lilac.config.APIConfig;
import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.KakaoBookSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import com.steelrain.springboot.lilac.exception.KakaoBookSearchException;
import com.steelrain.springboot.lilac.repository.IKaKoBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
