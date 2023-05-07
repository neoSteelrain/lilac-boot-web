package com.steelrain.springboot.lilac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

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

    @Value("${license.schd.api.key}")
    private String m_licenseSchdApikey;

    @Value("${licnese.schd.api.url}")
    private String m_licenseSchdUrl;

    @Value("${license.major.api.key}")
    private String m_licenseMajorApiKey;
    @Value("${license.major.api.url}")
    private String m_licenseMajorApiUrl;

    @Value("${naru.library.region.api.url}")
    private String m_libraryByRegionUrl;

    @Value("${aws.s3.accessKey}")
    private String m_awsS2AccessKey;

    @Value("${aws.s3.secretKey}")
    private String m_awsS3SecretKey;

    @Value("${aws.s3.bucket}")
    private String m_awsS3Bucket;

    @Value("${aws.s3.base.dir}")
    private String m_awsS3BaseDir;

    @Value("${aws.s3.bucket.dev}")
    private String m_awsS3BucketDev;

    private String m_activeProfile;


    @Autowired
    public APIConfiguration(ApplicationConfig applicationConfig){
        m_activeProfile = applicationConfig.getActiveProfile();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean APIConfig apiConfig(){
        return new APIConfig(m_youtubeKey,
                             m_KakaoKey,
                             m_naruLibraryByBookApiKey,
                             m_licenseSchdApikey,
                             m_licenseMajorApiKey,
                             m_kakaoBookSerchUrl,
                             m_naruLibraryByBookUrl,
                             m_naruBookExistUrl,
                             m_licenseSchdUrl,
                             m_licenseMajorApiUrl,
                             m_libraryByRegionUrl,
                             m_awsS2AccessKey,
                             m_awsS3SecretKey,
                             m_awsS3Bucket,
                             m_awsS3BucketDev,
                             m_awsS3BaseDir,
                             m_activeProfile);
    }
}
