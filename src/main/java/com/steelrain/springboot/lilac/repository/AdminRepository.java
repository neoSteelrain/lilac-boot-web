package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AdminRepository {
    private final AdminMapper m_adminMapper;

    public int insertRecommendedPlayList(List<Long> videoIdList) {
        return m_adminMapper.insertRecommendedPlayList(videoIdList);
    }

    public List<Long> selectAllPlayListId() {
        return m_adminMapper.selectAllPlayListLId();
    }
}
