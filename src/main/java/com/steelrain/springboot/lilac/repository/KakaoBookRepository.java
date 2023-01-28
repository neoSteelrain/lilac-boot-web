package com.steelrain.springboot.lilac.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steelrain.springboot.lilac.config.APIConfig;
import com.steelrain.springboot.lilac.datamodel.api.KakaoBookSearchResultDTO;
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

    @Override
    public KakaoBookSearchResultDTO searchBookfromKakao(String keyword) {
        List<NameValuePair> params = new ArrayList<>(4);
        params.add(new BasicNameValuePair("query", keyword));
        params.add(new BasicNameValuePair("sort", "accuracy"));
        params.add(new BasicNameValuePair("page", String.valueOf(1)));
        params.add(new BasicNameValuePair("size", String.valueOf(20)));

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
        KakaoBookSearchResultDTO result = null;
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            ClassicHttpResponse response = httpClient.execute(httpGet)){

            HttpEntity entity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(entity.getContent(), KakaoBookSearchResultDTO.class);
        }catch(IOException ex){
            throw new KakaoBookSearchException("카카오 책검색 API 호출시 예외발생", ex);
        }
        return result;
    }

    /* @Override
    public KakaoBookSearchResultDTO searchBookfromKakao(String keyword) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(m_apiConfig.getKakaoRestUrl());

        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        WebClient webClient = WebClient.builder()
                .uriBuilderFactory(uriBuilderFactory)
                .baseUrl(m_apiConfig.getKakaoRestUrl())
                .defaultHeader("Authorization", "KakaoAK " + m_apiConfig.getKakaoRestKey())
                .build();

        KakaoBookSearchResultDTO result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", keyword)
                        //.queryParam("sort", "accuracy")
                        .queryParam("page", String.valueOf(1))
                        .queryParam("size", String.valueOf(5))
                        .queryParam("target", "title")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KakaoBookSearchResultDTO.class)
                .block();
        return result;
    }*/
}
