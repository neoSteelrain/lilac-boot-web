package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.KakaoBookSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IKaKoBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BookServiceTests {

    @Autowired
    private IKaKoBookRepository kaKoBookRepository;

    @Test
    public void searchBookfromKakaoTest(){
        /*String tmp = "https://dapi.kakao.com/v3/search/book?query=정보처리기사 2022&page=1&size=5&target=title";
        char ch = tmp.charAt(50);
        System.out.println("ch : " + ch);*/

        KakaoBookSearchResultDTO res = kaKoBookRepository.searchBookfromKakao("정보처리기사 2022");
        System.out.println("res : " + res.toString());
        assertThat(res != null);
    }
}
