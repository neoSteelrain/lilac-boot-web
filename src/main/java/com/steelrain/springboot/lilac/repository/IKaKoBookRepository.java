package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.api.KakaoBookSearchResultDTO;

public interface IKaKoBookRepository {
    KakaoBookSearchResultDTO searchBookfromKakao(String keyword);
}
