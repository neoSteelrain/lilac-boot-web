package com.steelrain.springboot.lilac.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 아파치 HttpClient에 커넥션풀링을 설정하여 Bean으로 등록하는 클래스
 */
@Configuration
public class ApacheHttpClientConfig {
    private final int MAX_CONNECTION_COUNT = 5;
    private final int MAX_CONNECTION_ROUTE_COUNT = 5;

    @Bean
    public CloseableHttpClient apacheHttpClient(){
        return HttpClients.custom()
                .setConnectionManager(apachePoolingHttpClientConnectionManager())
                .build();
    }

    @Bean
    public PoolingHttpClientConnectionManager apachePoolingHttpClientConnectionManager(){
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnTotal(MAX_CONNECTION_COUNT)
                .setMaxConnPerRoute(MAX_CONNECTION_ROUTE_COUNT)
                .build();
    }


}
