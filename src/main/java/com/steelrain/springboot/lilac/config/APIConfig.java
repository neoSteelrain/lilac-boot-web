package com.steelrain.springboot.lilac.config;

import lombok.Getter;

@Getter
public class APIConfig {

    private String youtubeKey;
    private String kakaoRestKey;
    private String naruLibraryByBookApiKey;
    private String kakaoRestUrl;
    private String naruLibraryByBookUrl;

    private String naruBookExistUrl;

    public APIConfig(String youtube,
                     String kakaoRest,
                     String kakaoRestUrl,
                     String naruLibraryByBookUrl,
                     String naruLibraryByBookApiKey,
                     String naruBookExistUrl){
        this.youtubeKey = youtube;
        this.kakaoRestKey = kakaoRest;
        this.kakaoRestUrl = kakaoRestUrl;
        this.naruLibraryByBookUrl = naruLibraryByBookUrl;
        this.naruLibraryByBookApiKey = naruLibraryByBookApiKey;
        this.naruBookExistUrl = naruBookExistUrl;
    }
}
