package com.steelrain.springboot.lilac.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steelrain.springboot.lilac.config.APIConfig;
import com.steelrain.springboot.lilac.datamodel.api.LicenseScheduleResponseDTO;
import com.steelrain.springboot.lilac.exception.InvalidScheduleJsonException;
import com.steelrain.springboot.lilac.exception.LicenseScheduleException;
import com.steelrain.springboot.lilac.mapper.LicenseMapper;
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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * 국가자격 시험일정 조회 서비스 API 에서 자격증의 시험일정을 조회한다.
 */
@Repository
public class LicenseRepository implements ILicenseRespository{

    private final APIConfig m_ApiConfig;
    private final LicenseMapper m_licenseMapper;


    public LicenseRepository(APIConfig apiConfig, LicenseMapper licenseMapper){
        this.m_ApiConfig = apiConfig;
        this.m_licenseMapper = licenseMapper;
    }

    @Override
    public LicenseScheduleResponseDTO getLicenseSchedule(int licenseCode){
        String scheduleJsonStr = m_licenseMapper.getLicenseScheduleJsonString(licenseCode);
        if(!StringUtils.hasText(scheduleJsonStr)){
            String tmp = callLicenseScheduleAPI(licenseCode);
            if(m_licenseMapper.udpateLicenseScheduleJsonString(licenseCode, tmp) >= 1){
                scheduleJsonStr = tmp;
            }
        }

        LicenseScheduleResponseDTO result = null;
        try{
            result = new ObjectMapper().readValue(scheduleJsonStr, LicenseScheduleResponseDTO.class);
        }catch(JsonProcessingException pe){
            throw new InvalidScheduleJsonException("유효하지 않은 자격증시험일정 json 문자열", pe, scheduleJsonStr, licenseCode);
        }
        return result;
    }

    /*@Override
    public Optional<LicenseDTO> getLicenseInfo(int licenseCode) {
        LicenseDTO licenseDTO = new LicenseDTO();
        licenseDTO.setLicenseName(this.m_licenseMapper.getLicenseName(licenseCode));
        return Optional.ofNullable(licenseDTO);
    }*/

    @Override
    public Optional<String> getLicenseName(int licenseCode) {
        return Optional.ofNullable(m_licenseMapper.getLicenseName(licenseCode));
    }

    private String callLicenseScheduleAPI(int licenseCode){
        HttpGet httpGet = new HttpGet(m_ApiConfig.getLicenseSchdUrl());
        List<NameValuePair> nvp = new ArrayList<>(6);
        nvp.add(new BasicNameValuePair("serviceKey", m_ApiConfig.getLicenseSchdApikey()));

        /*
        1페이지 결과수
        50개 까지가 API가 정한 한계수치, 1년 동안 50번 이상 치루는 시험은 없을것이다.
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
            throw new LicenseScheduleException("공공데이터 자격증시험일정 API 호출시 에외 발생", urie, licenseCode);
        }

        String result = null;
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            ClassicHttpResponse response = httpClient.execute(httpGet)){

            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        }catch (IOException | ParseException ex){
            throw new LicenseScheduleException("공공데이터 자격증시험일정 API 호출시 에외 발생", ex, licenseCode);
        }
        return result;
    }
}
