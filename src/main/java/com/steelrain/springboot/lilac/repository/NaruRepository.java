package com.steelrain.springboot.lilac.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steelrain.springboot.lilac.config.APIConfig;
import com.steelrain.springboot.lilac.datamodel.api.NaruBookExistResposeDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByBookResponseDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByRegionResponseDTO;
import com.steelrain.springboot.lilac.exception.NaruBookExistException;
import com.steelrain.springboot.lilac.exception.NaruLibraryByBookException;
import com.steelrain.springboot.lilac.mapper.LicenseBookMapper;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class NaruRepository implements INaruRepository{
    private final APIConfig m_apiConfig;
    private final CloseableHttpClient m_httpClient;


    @Override
    public NaruLibSearchByBookResponseDTO getLibraryByBook(long isbn, short region, int detailRegion) {
        List<NameValuePair> params = new ArrayList<>(7);
        params.add(new BasicNameValuePair("authKey", m_apiConfig.getNaruLibraryByBookApiKey()));
        params.add(new BasicNameValuePair("isbn", String.valueOf(isbn)));
        /*
            naru API에서 region 코드는 필수이지만 detialRegion 코드는 선택입력이다.
            각 코드는 숫자형이 아닌 문자열로 입력받기 때문에 호출할때에 변환해서 준다.
         */
        params.add(new BasicNameValuePair("region", String.valueOf(region)));
        if(detailRegion > 0){
            params.add(new BasicNameValuePair("dtl_region", String.valueOf(detailRegion)));
        }
        params.add(new BasicNameValuePair("pageNo", String.valueOf(1)));
        params.add(new BasicNameValuePair("pageSize", String.valueOf(50)));
        params.add(new BasicNameValuePair("format", "json"));

        HttpGet httpGet = new HttpGet(m_apiConfig.getNaruLibraryByBookUrl());
        try{
            URI uri = new URIBuilder(new URI(m_apiConfig.getNaruLibraryByBookUrl()))
                    .addParameters(params)
                    .build();
            httpGet.setUri(uri);
        }catch (URISyntaxException urie){
            throw new NaruLibraryByBookException("나루 소장도서관 검색 예외", urie);
        }
        NaruLibSearchByBookResponseDTO result = null;
        try(ClassicHttpResponse response = m_httpClient.execute(httpGet)){
            HttpEntity entity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(entity.getContent(), NaruLibSearchByBookResponseDTO.class);
        }catch (IOException ex){
            throw new NaruLibraryByBookException("나루 소장도서관 검색 예외", ex);
        }
        return result;
    }

    public NaruLibSearchByRegionResponseDTO getLibraryByRegion(short region, int detailRegion){
        List<NameValuePair> params= new ArrayList<>(6);
        params.add(new BasicNameValuePair("authKey", m_apiConfig.getNaruLibraryByBookApiKey()));
        /*
            naru API에서 region 코드는 필수이지만 detialRegion 코드는 선택입력이다.
            각 코드는 숫자형이 아닌 문자열로 입력받기 때문에 호출할때에 변환해서 준다.
         */
        params.add(new BasicNameValuePair("region", String.valueOf(region)));
        if(detailRegion > 0){
            params.add(new BasicNameValuePair("dtl_region", String.valueOf(detailRegion)));
        }
        params.add(new BasicNameValuePair("pageNo", String.valueOf(1)));
        params.add(new BasicNameValuePair("pageSize", String.valueOf(20)));
        params.add(new BasicNameValuePair("format", "json"));

        HttpGet httpGet = new HttpGet(m_apiConfig.getLibraryByRegionUrl());
        try{
            URI uri = new URIBuilder(new URI(m_apiConfig.getLibraryByRegionUrl()))
                    .addParameters(params)
                    .build();
            httpGet.setUri(uri);
        }catch (URISyntaxException urie){
            throw new NaruLibraryByBookException("나루 정보공개도서관 검색 예외", urie);
        }
        NaruLibSearchByRegionResponseDTO result = null;
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            ClassicHttpResponse response = httpClient.execute(httpGet)){
            HttpEntity entity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(entity.getContent(), NaruLibSearchByRegionResponseDTO.class);
        }catch (IOException ex){
            throw new NaruLibraryByBookException("나루 정보공개도서관 검색 예외", ex);
        }
        return result;
    }

    @Override
    public NaruBookExistResposeDTO checkBookExist(long isbn, int libCode) {
        List<NameValuePair> params = new ArrayList<>(4);
        params.add(new BasicNameValuePair("authKey", m_apiConfig.getNaruLibraryByBookApiKey()));
        params.add(new BasicNameValuePair("isbn13", String.valueOf(isbn)));
        params.add(new BasicNameValuePair("libCode", String.valueOf(libCode)));
        params.add(new BasicNameValuePair("format", "json"));

        HttpGet httpGet = new HttpGet(m_apiConfig.getNaruBookExistUrl());
        try{
            URI uri = new URIBuilder(new URI(m_apiConfig.getNaruBookExistUrl()))
                    .addParameters(params)
                    .build();
            httpGet.setUri(uri);
        }catch (URISyntaxException urie){
            throw new NaruBookExistException("나루 도서소장여부 조회 예외", urie);
        }
        NaruBookExistResposeDTO result = null;
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            ClassicHttpResponse response = httpClient.execute(httpGet)){
            HttpEntity entity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(entity.getContent(), NaruBookExistResposeDTO.class);
        }catch (IOException ex){
            throw new NaruBookExistException("나루 도서소장여부 조회 예외", ex);
        }
        return result;
    }
}
