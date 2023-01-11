package com.steelrain.springboot.lilac.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * API 설정정보를 읽어오는 구성클래스
 */
@Configuration
@PropertySource("classpath:api-keys.properties")
public class APIConfiguration {

    @Value("${youtube.api.key}")
    private String m_youtubeKey;

    @Value("${kakao.rest.api.key}")
    private String m_KakaoKey;

    @Value("${naru.library.by.book.api.key}")
    private String m_naruLibraryByBookApiKey;

    @Value("${kakao.rest.api.booksearch.url}")
    private String m_kakaoBookSerchUrl;

    @Value("${naru.library.by.book.api.url}")
    private String m_naruLibraryByBookUrl;

    @Value("${naru.library.book.exist.api.url}")
    private String m_naruBookExistUrl;

    @Value("${license.schd.apikey}")
    private String m_licenseSchdApikey;

    @Value("${licnese.schd.apikey.url}")
    private String m_licenseSchdUrl;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean APIConfig apiConfig(){
        return new APIConfig(m_youtubeKey,
                             m_KakaoKey,
                             m_naruLibraryByBookApiKey,
                             m_licenseSchdApikey,
                             m_kakaoBookSerchUrl,
                             m_naruLibraryByBookUrl,
                             m_naruBookExistUrl,
                             m_licenseSchdUrl);
    }
}
