package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.api.KakaoBookSearchResponseDTO;

/**
 * 카카오 REST API 연동을 담당한다
 */
public interface IKaKoBookRepository {

    /**
     * 도서제목이 키워드 조건에 맞는 도서목옥을 반환한다
     * @param keyword 도서제목에 조회할 키워드
     * @return 검색된 도서목록
     */
    KakaoBookSearchResponseDTO searchBookfromKakao(String keyword);
}
