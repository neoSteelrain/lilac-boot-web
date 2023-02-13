package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.view.BookDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
public class SearchServiceTests {

    @Autowired
    ISearchService m_searchService;

    @Test
    @DisplayName("도서의 상세정보 테스트")
    public void 도서의상세정보테스트하기(){
        BookDetailDTO dto = m_searchService.getBookDetailInfo(1L, (short) 23, 23040);
        assertThat(Objects.isNull(dto)).isFalse();
        log.debug("BookDetailDTO : "+dto.toString());
    }

    @Test
    @DisplayName("BookService 쓰레기값 테스트")
    public void 도서상세정보의쓰레기값테스트하기(){
        BookDetailDTO dto = m_searchService.getBookDetailInfo(1L, (short) 0, 0);
        assertThat(Objects.isNull(dto)).isFalse();
        assertThat(dto.getLibraryList() != null).isTrue();
        assertThat(dto.getLibraryList().size() == 0).isTrue();
        log.debug("BookDetailDTO : "+dto.toString());
    }
}
