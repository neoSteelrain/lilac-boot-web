package com.steelrain.springboot.lilac.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steelrain.springboot.lilac.config.APIConfig;
import com.steelrain.springboot.lilac.datamodel.api.KakaoBookSearchResponseDTO;
import com.steelrain.springboot.lilac.exception.KakaoBookSearchException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class KakaoBookRepository implements IKaKoBookRepository{

    private final APIConfig m_apiConfig;

    public KakaoBookRepository(APIConfig apiConfig){
        this.m_apiConfig = apiConfig;
    }

    /**
     * 카카오 책검색 REST API를 이용하여 도서정보를 조회하는 클래스
     * @param keyword 도서제목에 조회할 키워드
     * @param pageNum 페이지 번호
     * @param bookCount 페이지당 보여질 책의 개수
     * @return 카카오 REST API의 json응답을 객체로 매핑하여 반환 : DTO Generator 유틸로 자동생성하였다
     */
    @Override
    public KakaoBookSearchResponseDTO searchBookFromKakao(String keyword, int pageNum, int bookCount) {
        List<NameValuePair> params = new ArrayList<>(4);
        params.add(new BasicNameValuePair("query", keyword));
        params.add(new BasicNameValuePair("sort", "accuracy"));
        params.add(new BasicNameValuePair("page", String.valueOf(pageNum)));
        params.add(new BasicNameValuePair("size", String.valueOf(bookCount)));

        HttpGet httpGet = new HttpGet(m_apiConfig.getKakaoRestUrl());
        try{
            URI uri = new URIBuilder(new URI(m_apiConfig.getKakaoRestUrl()))
                    .addParameters(params)
                    .build();
            httpGet.setUri(uri);
            httpGet.addHeader("Authorization", "KakaoAK " + m_apiConfig.getKakaoRestKey());
        }catch (URISyntaxException urie){
            throw new KakaoBookSearchException("카카오 책검색 API 호출시 예외발생", urie);
        }
        KakaoBookSearchResponseDTO result = null;
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            ClassicHttpResponse response = httpClient.execute(httpGet)){

            HttpEntity entity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(entity.getContent(), KakaoBookSearchResponseDTO.class);
        }catch(IOException ex){
            throw new KakaoBookSearchException("카카오 책검색 API 호출시 예외발생", ex);
        }
        return result;
    }
}
