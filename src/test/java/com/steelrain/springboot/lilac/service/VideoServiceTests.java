package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.KEYWORD_TYPE;
import com.steelrain.springboot.lilac.datamodel.VideoPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteYoutubeVideoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
public class VideoServiceTests {

    @Autowired
    private IVideoService m_videoService;

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

    @Test
    @Transactional
    public void 재생시간_업데이트(){
        m_videoService.updateVideoPlaytime(1L, 60L);
    }

    @Test
    public void 강의노트에추가된재생목록가져오기(){
        List<LectureNoteYoutubeVideoDTO> list = m_videoService.getPlayListDetailOfLectureNote(2L, 49L, 2L);

        assertThat(list != null).isTrue();
        list.stream().forEach(item -> log.debug(item.toString()));
    }

    @Test
    public void 강의노트영상의Duration가져오기(){
        m_videoService.updateVideoPlaytime(1L, 1100L);
    }

    @Test
    public void 좋아요영상처리후_결과맵가져오기(){
        Map<String, Long> res = m_videoService.updateLikeVideo(187L, 2L);
        res.keySet().forEach(key -> System.out.println("키값 : "+key));
        res.values().forEach(val -> System.out.println("값 : "+val));

        System.out.println("lilac_like_count : "+res.get("lilac_like_count"));
        System.out.println("lilac_dislike_count : "+res.get("lilac_dislike_count"));
        assertThat(Objects.nonNull(res)).isTrue();
    }

    @Test
    @DisplayName("자격증재생목록을_가져오기")
    public void 자격증재생목록을_가져오기(){
        VideoPlayListSearchResultDTO res = m_videoService.searchPlayListById(1320, 1, 5, KEYWORD_TYPE.LICENSE);
        assertThat(res.getPlayList().size() > 0).isTrue();
        log.debug("list 정보 : {}", res.getPlayList());
    }

    @Test
    @DisplayName("키워드재생목록을_가져오기")
    public void 키워든재생목록을_가져오기(){
        VideoPlayListSearchResultDTO res = m_videoService.searchPlayListById(100, 1, 5, KEYWORD_TYPE.SUBJECT);
        assertThat(res.getPlayList().size() > 0).isTrue();
        log.debug("list 정보 : {}", res.getPlayList());
    }
}
