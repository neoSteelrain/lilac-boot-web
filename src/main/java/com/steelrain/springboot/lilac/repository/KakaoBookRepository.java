package com.steelrain.springboot.lilac.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steelrain.springboot.lilac.config.APIConfig;
import com.steelrain.springboot.lilac.datamodel.api.KakaoBookSearchResponseDTO;
import com.steelrain.springboot.lilac.exception.KakaoBookSearchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class KakaoBookRepository implements IKaKoBookRepository{

    private final APIConfig m_apiConfig;
    private final CloseableHttpClient m_httpClient;



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
        try(ClassicHttpResponse response = m_httpClient.execute(httpGet)){

            if(log.isDebugEnabled()){
                log.debug("=======> 카카오 HTTP 응답헤더 내용 시작 ===========");
                for(Header header : response.getHeaders()){
                    log.debug("헤더 키 : {} , 헤더 값 : {}", header.getName(), header.getValue());
                }
                log.debug("=======> 카카오 HTTP 응답헤더 내용 끝 ===========");
            }

            HttpEntity entity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(entity.getContent(), KakaoBookSearchResponseDTO.class);
            EntityUtils.consume(entity);
        }catch(IOException ex){
            throw new KakaoBookSearchException("카카오 책검색 API 호출시 예외발생", ex);
        }
        return result;
    }
}
