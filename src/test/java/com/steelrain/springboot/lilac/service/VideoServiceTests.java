package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
public class VideoServiceTests {

    @Autowired
    private VideoService m_videoService;

    @Test
    @DisplayName("재생목록의 영상들 가져오기")
    public void testGetPlayListDetail(){
        List<YoutubeVideoDTO> videoDTOList = m_videoService.getPlayListDetail(1L);

        assertThat(videoDTOList != null);
        videoDTOList.stream().forEach(video -> {
            //System.out.println(String.format("=== 영상정보들 : %s \n", video.toString()));
            log.info("=== 영상정보들 : {}", video);
        });
    }

    @Test
    public void 영상정보조회(){
        YoutubeVideoDTO dto = m_videoService.getVideoDetail(23L);

        assertThat(dto != null);
        log.debug("영상정보 : {}", dto);
    }
}
