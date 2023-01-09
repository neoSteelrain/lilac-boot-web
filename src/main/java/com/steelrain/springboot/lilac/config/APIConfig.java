package com.steelrain.springboot.lilac.config;

import lombok.Getter;

@Getter
public class APIConfig {

    private String youtubeKey;
    private String kakaoRestKey;
    private String kakaoRestUrl;

    public APIConfig(String youtube, String kakaoRest, String kakaoRestUrl){
        this.youtubeKey = youtube;
        this.kakaoRestKey = kakaoRest;
        this.kakaoRestUrl = kakaoRestUrl;
    }
}
