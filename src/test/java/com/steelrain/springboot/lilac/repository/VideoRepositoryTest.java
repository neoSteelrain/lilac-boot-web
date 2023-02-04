package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import lombok.extern.slf4j.Slf4j;
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
        List<YoutubePlayListDTO> list = repository.findPlayListByKeyword("정보", 0, 6);

        assertThat(list != null);
        list.forEach(pl -> {
            log.debug("재생목록 : {}", pl);
        });
    }
}