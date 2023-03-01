package com.steelrain.springboot.lilac.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class AwsS3ClientConfig {

    private final APIConfig m_apiConfig;


    @Bean
    public S3Client amazonS3Client(){
        AwsBasicCredentials credentials = AwsBasicCredentials.create(m_apiConfig.getAwsS3accessKey(), m_apiConfig.getAwsS3SecretKey());
        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
