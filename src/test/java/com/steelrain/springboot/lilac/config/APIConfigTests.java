package com.steelrain.springboot.lilac.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.util.StringUtils;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class APIConfigTests {

    @Autowired
    private APIConfig m_apiConfig;

    /**
     * APIConfig 빈이 외부 API키값들을 정상적으로 로드하는지 테스트한다.
     */
    @Test
    public void testAPIConfig(){
        String youtube = m_apiConfig.getYoutubeKey();
        String kakao = m_apiConfig.getKakaoRestKey();
        String kakao_url = m_apiConfig.getKakaoRestUrl();
        System.out.println("youtube : " + youtube);
        System.out.println("kakao : " + kakao);
        System.out.println("kakao-url : " + kakao_url);
        assertThat(new boolean[]{ !StringUtils.isEmpty(youtube), !StringUtils.isEmpty(kakao), !StringUtils.isEmpty(kakao_url)});
    }
}
