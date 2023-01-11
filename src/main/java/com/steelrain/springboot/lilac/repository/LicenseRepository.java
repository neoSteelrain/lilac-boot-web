package com.steelrain.springboot.lilac.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steelrain.springboot.lilac.config.APIConfig;
import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import com.steelrain.springboot.lilac.datamodel.api.LicenseScheduleResponseDTO;
import com.steelrain.springboot.lilac.exception.LicenseScheduleException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 국가자격 시험일정 조회 서비스 API 에서 자격증의 시험일정을 조회한다.
 */
@Repository
public class LicenseRepository implements ILicenseRespository{

    private final APIConfig m_ApiConfig;

    public LicenseRepository(APIConfig apiConfig){
        this.m_ApiConfig = apiConfig;
    }

    @Override
    public List<LicenseScheduleResponseDTO.LicenseSchedule> getLicenseInfo(int licenseCode){
        LicenseScheduleResponseDTO responseDTO = callLicenseScheduleAPI(licenseCode);
        return responseDTO.getBody().getItems();
    }

    private LicenseScheduleResponseDTO callLicenseScheduleAPI(int licenseCode){
        HttpGet httpGet = new HttpGet(m_ApiConfig.getLicenseSchdUrl());
        List<NameValuePair> nvp = new ArrayList<>(6);
        nvp.add(new BasicNameValuePair("serviceKey", m_ApiConfig.getLicenseSchdApikey()));

        /*
        1페이지 결과수
        50개 까지가 API가 정한 한계수치, 1년 동안 50번 이상 치루는 시험은 없을것이다.
        TODO: 50개가 넘는다면 페이징처리를 해서 모든 일정을 다 가져오는 로직을 추가해야 한다.
         */
        nvp.add(new BasicNameValuePair("numOfRows", "50"));
        nvp.add(new BasicNameValuePair("pageNo", "1"));
        nvp.add(new BasicNameValuePair("dataFormat", "json"));
        // 적용년도는 올해로 세팅한다.
        nvp.add(new BasicNameValuePair("implYy", String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
        /*
        자격구분코드
        T : 국가기술자격
        C : 과정평가형
        W : 일학습병행자격
        S : 국가전문자격
        정보처리기사 같은 정보기술 분야는 국가기술자격에 속하므로 T로 설정한다.
         */
        nvp.add(new BasicNameValuePair("qualgbCd", "T"));
        // 종목코드 세팅
        nvp.add(new BasicNameValuePair("jmCd", String.valueOf(licenseCode)));
        try{
            URI uri = new URIBuilder(new URI(m_ApiConfig.getLicenseSchdUrl()))
                    .addParameters(nvp)
                    .build();
            httpGet.setUri(uri);
        }catch (URISyntaxException urie){
            throw new LicenseScheduleException("공공데이터 자격증시험일정 API 호출시 에외 발생", urie);
        }

        LicenseScheduleResponseDTO result = null;
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            ClassicHttpResponse response = httpClient.execute(httpGet)){

            HttpEntity entity = response.getEntity();
            result = new ObjectMapper().readValue(entity.getContent(), LicenseScheduleResponseDTO.class);

        }catch (IOException ioe){
            throw new LicenseScheduleException("공공데이터 자격증시험일정 API 호출시 에외 발생", ioe);
        }
        return result;
    }
}
