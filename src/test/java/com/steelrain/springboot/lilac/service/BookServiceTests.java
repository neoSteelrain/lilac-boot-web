package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.api.KakaoSearchedBookDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByBookResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BookServiceTests {

    @Autowired
    private IBookService bookService;

    @Test
    public void testGetKakaoBookLibraryList(){
        Map<KakaoSearchedBookDTO, List<NaruLibSearchByBookResponseDTO.Library>> res = bookService.getKakaoBookLibraryList("이것이 취업을 위한 코딩 테스트다 with 파이썬", (short)23, 23040);
        // res.getResponse().getLibs().stream().forEach(lib -> System.out.println(lib.getLib().getLibname()));
        res.keySet().stream().forEach(book -> System.out.println(book.getTitle()));
        Collection<List<NaruLibSearchByBookResponseDTO.Library>> libs = res.values();
        libs.forEach(list -> System.out.println(list.toString()));

        System.out.println("--------------------------");
        Set<KakaoSearchedBookDTO> tmp = res.keySet();
        for(KakaoSearchedBookDTO dto : tmp){
            res.get(dto).stream().forEach(lib -> System.out.println(lib.getLibname() + " : " + lib.getLoanAvailable()));
        }
    }
}
