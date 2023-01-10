package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.api.KakaoBookSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruBookExistResposeDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByBookResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RepositoryTesets {

    @Autowired
    private IKaKoBookRepository kaKoBookRepository;

    @Autowired
    private INaruRepository naruRepository;


    @Test
    public void testSearchBookfromKakao(){
        /*String tmp = "https://dapi.kakao.com/v3/search/book?query=정보처리기사 2022&page=1&size=5&target=title";
        char ch = tmp.charAt(50);
        System.out.println("ch : " + ch);*/

        KakaoBookSearchResultDTO res = kaKoBookRepository.searchBookfromKakao("정보처리기사 2022");
        System.out.println("res : " + res.toString());
        assertThat(res != null);
    }

    @Test
    public void testGetLibraryByBook(){
        // 테스트 ISBN 9791162243077
        NaruLibSearchByBookResponseDTO res = naruRepository.getLibraryByBook(9791162243077L, (short)23, 23090);
        System.out.println("res : " + res.toString());
        res.getResponse().getLibs().stream().forEach(lib -> System.out.println(lib.getLib().getLibname()));
        assertThat(res != null);
    }

    @Test
    public void testByGabageGetLibraryByBook(){
        NaruLibSearchByBookResponseDTO res = naruRepository.getLibraryByBook(9791162243077L, (short)23, 23040);
        for(NaruLibSearchByBookResponseDTO.Libs ls : res.getResponse().getLibs()){
            System.out.println("Library : " + ls.getLib().getLibname());
        }

//        System.out.println("res : " + res.getResponse().getError());
        //res.getResponse().getLibs().stream().forEach(lib -> System.out.println(lib.getLib().getLibname()));
        assertThat(res != null);
    }

    @Test
    public void testCheckBookExist(){
        NaruBookExistResposeDTO res = naruRepository.checkBookExist(01020, (short)23090);
        // NaruBookExistResposeDTO res = naruRepository.checkBookExist(9791162243077L, (short)1111);
        System.out.println("res : " + res.getResponse().getError());
        assertThat(res != null);
    }

}
