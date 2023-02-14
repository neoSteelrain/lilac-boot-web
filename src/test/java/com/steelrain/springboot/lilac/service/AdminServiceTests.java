package com.steelrain.springboot.lilac.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        List<Long> plIdList = m_adminService.getAllPlayListId();
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
        assertThat(isAdded).isTrue();
    }
}
