package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class VideoRepositoryTest {

    @Autowired
    private IVideoRepository repository;


    @Test
    void findPlayListByKeyword(){
        List<YoutubePlayListDTO> list = repository.findPlayListByKeyword("자바", 0, 6);

        assertThat(list != null);
        list.forEach(pl -> {
            log.debug("재생목록 : {}", pl);
        });
    }
    
    @Test
    @DisplayName("검색조건이 자격증인 경우 동적쿼리로 실행")
    public void testSelectTotalPlayListCountLicense(){
        int val = repository.selectTotalPlayListCount(1, 1);
        assertThat(val > 0).isTrue();
    }

    @Test
    @DisplayName("검색조건이 키워드인 경우 동적쿼리로 실행")
    public void testSelectTotalPlayListCountSubject(){
        int val = repository.selectTotalPlayListCount(1, 2);
        assertThat(val > 0).isTrue();
    }
}