package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class AdminServiceTests {

    @Autowired
    AdminService m_adminService;

    @Autowired
    IVideoService m_videoService;

    @Test
    @Rollback
    public void 추천재생목록생성_테스트(){
        /*List<Long> plIdList = m_adminService.getAllPlayListId();
        Random random = new Random(plIdList.size());
        List<Integer> randIds = new ArrayList<>(6);
        boolean isExit = true;
        while(isExit){
            Integer num = random.nextInt(plIdList.size());
            if(num == 0){
                continue;
            }
            if(!randIds.contains(num) && m_videoService.isExistYoutubePlayList((Long.valueOf(num)))) {
                randIds.add(num);
            }
            isExit = randIds.size() <= 6;
        }

        List<Long> idListParam = new ArrayList<>(6);
        randIds.forEach(id -> {
            log.debug("전체 재생목록개수 : {} , 랜덤하게 만든 재생목록 개의 id 값 : {}", plIdList.size(), id);
            idListParam.add(Long.valueOf(id));
        });
        boolean isAdded = m_adminService.addRecommendedPlayList(idListParam);
        assertThat(isAdded).isTrue();*/
    }

    @Test
    public void 모든재생목록리스트가져오기(){
        /*AdminPlayListSearchResultDTO res = m_adminService.getAllPlayList(1, 20, new String[]{"정보처리기사","정보처리산업기사"});

        assertThat(!Objects.isNull(res)).isTrue();
        log.debug("pageDTO : {}", res.getPageDTO());
        log.debug("playlist count : {}", res.getPlaylist().size());*/
    }

    @Test
    public void 오늘날짜구하기(){
        String today = LocalDate.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59"));
        log.debug("today : {}", today);

        //log.debug("getDayOfWeek : {}",LocalDate.now(ZoneId.of("Asia/Seoul")).getDayOfMonth());
        String yesterday = LocalDate.now(ZoneId.of("Asia/Seoul")).minusDays(1L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 23:59:59"));
        log.debug("yesterday : {}", yesterday);
    }

    @Test
    public void 오늘추가된재생목록가져오기(){
        AdminPlayListSearchResultDTO res = m_adminService.getTodayPlayList(1, 20, new int[]{1,2,3}, null);
        assertThat(!Objects.isNull(res)).isTrue();
        log.debug("pageDTO : {}", res.getPageDTO());
        log.debug("playlist count : {}", res.getPlaylist().size());
    }

    @Test
    public void 자격증조건재생목록가져오기(){
        AdminPlayListSearchResultDTO res = m_adminService.getAllPlayList(0, 10, new int[]{1,2,3}, null);
        assertThat(Objects.nonNull(res)).isTrue();
        log.debug("pageDTO : {}", res.getPageDTO());
        log.debug("playlist count : {}", res.getPlaylist().size());
    }

    @Test
    public void 키워드조건재생목록가져오기(){
        AdminPlayListSearchResultDTO res = m_adminService.getAllPlayList(0, 10, null, new int[]{1,2,3,4,5,6});
        assertThat(Objects.nonNull(res)).isTrue();
        log.debug("pageDTO : {}", res.getPageDTO());
        log.debug("playlist count : {}", res.getPlaylist().size());
    }

    @Test
    public void 모든조건재생목록가져오기(){
        AdminPlayListSearchResultDTO res = m_adminService.getAllPlayList(0, 10, new int[]{1,2,3}, new int[]{1,2,3,4,5,6});
        assertThat(Objects.nonNull(res)).isTrue();
        log.debug("pageDTO : {}", res.getPageDTO());
        log.debug("playlist count : {}", res.getPlaylist().size());
    }

    @Test
    public void 추천재생목록후보추가(){
         boolean res = m_adminService.addCandiPlayList(168L);
         assertThat(res).isTrue();
    }
}
