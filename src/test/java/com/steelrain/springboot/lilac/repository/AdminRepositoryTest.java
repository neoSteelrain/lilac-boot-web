package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.AdminYoutubePlayListDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class AdminRepositoryTest {

    @Autowired
    private IAdminRepository m_repo;

    @Test
    @DisplayName("좋아요순서로 재생목록 자격증 조회")
    void findLicensePlayListByLike() {
        List<AdminYoutubePlayListDTO> pl = m_repo.findPlayListByLike(true, new int[]{1}, null, 0, 10);
        assertThat(pl.size() > 0).isTrue();
        pl.stream().forEach(p -> log.debug("pl 정보 : {}", p));
        log.debug("자격증 pl 길이 : {}",pl.size());
    }

    @Test
    @DisplayName("좋아요순서로 재생목록 키워드 조회")
    void findSubjectPlayListByLike() {
        List<AdminYoutubePlayListDTO> pl = m_repo.findPlayListByLike(true, null, new int[]{1,2,3}, 0, 10);
        assertThat(pl.size() > 0).isTrue();
        pl.stream().forEach(p -> log.debug("pl 정보 : {}", p));
        log.debug("키워드 pl 길이 : {}",pl.size());
    }

    @Test
    @DisplayName("좋아요순서로 모든 자격증 키워드 조회")
    void findAllPlayListByLike(){
        List<AdminYoutubePlayListDTO> pl = m_repo.findPlayListByLike(true, new int[] {1}, new int[]{1,2,3}, 0, 10);
        assertThat(pl.size() > 0).isTrue();
        pl.stream().forEach(p -> log.debug("pl 정보 : {}", p));
        log.debug("전체 pl 길이 : {}",pl.size());
    }
    
    @Test
    @DisplayName("가장많이본 순서로 재생목록 자격증 조회")
    public void findLicensePlayListByViewCount(){
        List<AdminYoutubePlayListDTO> pl = m_repo.findPlayListByViewCount(true, new int[]{1}, null, 0, 10);
        assertThat(pl.size() > 0).isTrue();
    }
}