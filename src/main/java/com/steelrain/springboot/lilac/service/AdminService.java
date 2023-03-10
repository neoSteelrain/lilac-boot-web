package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 관리자 서비스
 * - 관리자의 비즈니스 로직을 구현
 * - 관리자 관련된 이벤트를 처리/발행 한다 
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository m_adminRepository;


    @Transactional(rollbackFor = Exception.class)
    public boolean addRecommendedPlayList(List<Long> videoIdList){
        return m_adminRepository.insertRecommendedPlayList(videoIdList) == videoIdList.size();
    }

    public List<Long> getAllPlayListId(){
        return m_adminRepository.selectAllPlayListId();
    }
}
