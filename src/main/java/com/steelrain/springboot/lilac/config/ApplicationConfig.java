package com.steelrain.springboot.lilac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * application.yml 파일의 설정값을 얻어오는 구성클래슨
 * 외부 API 정보는 api-keys.properties 파일에서 관리하는 때문에
 * spring 프레임워크의 설정 정보를 다루는 클래스는 따로 분리하였다
 */
@Configuration
public class ApplicationConfig {

    private Environment m_env;

    @Autowired
    public ApplicationConfig(Environment env){
        this.m_env = env;
    }

    /**
     * application.yml 파일의 설정정보들 중에서 spring.profiles.active 설정값을 반환한다
     * @return spring.profiles.active 설정값 : dev, aws 2개중 하나의 값
     */
    public String getActiveProfile(){
        return m_env.getProperty("spring.profiles.active");
    }
}
