package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.DateUtils;
import com.steelrain.springboot.lilac.common.ICacheService;
import com.steelrain.springboot.lilac.common.PeriodDate;
import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.repository.AdminRepository;
import com.steelrain.springboot.lilac.repository.IAdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 관리자 서비스
 * - 관리자의 비즈니스 로직을 구현
 */
@Slf4j
@Service
public class AdminPlayListService implements IAdminPlayListService {
    private final IAdminRepository m_adminRepository;
    private final Map<ADMIN_PLAYLIST_TYPE, IPlayListFinder> m_plFinderMap;


    @Autowired
    public AdminPlayListService(AdminRepository repository){
        this.m_adminRepository = repository;

        /*
            - ADMIN_PLAYLIST_TYPE 에 대한 조건문을 대신 IPlayListFinder 인터페이스를 구현해서 map 에 저장하도록 한다.
            TODO 재생목록 조회조건이 변경되거나 추가되면 이부분을 수정해야 한다.
         */
        m_plFinderMap = new HashMap<>(ADMIN_PLAYLIST_TYPE.values().length);
        m_plFinderMap.put(ADMIN_PLAYLIST_TYPE.ALL, new AllPlayListFinder());
        m_plFinderMap.put(ADMIN_PLAYLIST_TYPE.TODAY, new TodayPlayListFinder());
        m_plFinderMap.put(ADMIN_PLAYLIST_TYPE.WEEK, new WeekPlayListFinder());
        m_plFinderMap.put(ADMIN_PLAYLIST_TYPE.MONTH, new MonthPlayListFinder());
        m_plFinderMap.put(ADMIN_PLAYLIST_TYPE.LIKE_HIGH, new LikeCountPlayListFinder(true));
        m_plFinderMap.put(ADMIN_PLAYLIST_TYPE.LIKE_LOW, new LikeCountPlayListFinder(false));
        m_plFinderMap.put(ADMIN_PLAYLIST_TYPE.VIEW_HIGH, new ViewCountPlayListFinder(true));
        m_plFinderMap.put(ADMIN_PLAYLIST_TYPE.VIEW_LOW, new ViewCountPlayListFinder(false));
    }

    /**
     * 재생목록에 대한 모든 기능은 ADMIN_PLAYLIST_TYPE 으로 정의해야 한다
     * IPlayListFinder 인터페이스를 구현한 객체이어야 한다
     * ADMIN_PLAYLIST_TYPE 으로 정의된
     * @param type
     * @param pageNum
     * @param pageCount
     * @param licenseIds
     * @param subjectIds
     * @return
     */
    @Override
    public AdminPlayListSearchResultDTO getAdminPlayList(ADMIN_PLAYLIST_TYPE type, int pageNum, int pageCount, int[] licenseIds, int[] subjectIds) {
        return m_plFinderMap.get(type).getPlayList(pageNum, pageCount, licenseIds, subjectIds, m_adminRepository);
    }

    @Override
    public int getTotalPlayListCount() {
        return m_adminRepository.findTotalPlayListCount();
    }

    @Override
    public int getTodayPlayListCount() {
        PeriodDate date = DateUtils.getToday();
        return m_adminRepository.findPlayListCountByRange(date.getFromDate(), date.getToDate());
    }

    @Override
    public int getWeekPlayListCount() {
        PeriodDate fromDate = DateUtils.getMondayOfWeek();
        PeriodDate toDate = DateUtils.getSundayOfWeek();
        return m_adminRepository.findPlayListCountByRange(fromDate.getFromDate(), toDate.getToDate());
    }

    @Override
    public int getMonthPlayListCount() {
        PeriodDate fromDate = DateUtils.getFirstdayOfMonth();
        PeriodDate toDate = DateUtils.getLastdayOfMonth();
        return m_adminRepository.findPlayListCountByRange(fromDate.getFromDate(), toDate.getToDate());
    }

    @Override
    @Transactional
    public boolean addCandiPlayList(Long playListId) {
        return m_adminRepository.addCandiPlayList(playListId) > 0;
    }

    // 추천재생목록 후보목록을 가져온다
    @Override
    public AdminPlayListSearchResultDTO getCandiPlayList() {
        return AdminPlayListSearchResultDTO.builder()
                .playlist(m_adminRepository.findCandiPlayList())
                .build();
    }

    /*
        - 추천재생목록을 가져온다
        - 사용자에게 실제로 보여지는 정보이다.
     */
    @Override
    public AdminPlayListSearchResultDTO getRecommendPlayList() {
        return AdminPlayListSearchResultDTO.builder()
                .playlist(m_adminRepository.findRecommendPlayList())
                .build();
    }

    // 추천재생목록을 삭제한다
    @Override
    @Transactional
    public AdminPlayListSearchResultDTO removeRecommendPlayList(Long playListId) {
        m_adminRepository.removeRecommendPlayList(playListId);
        return AdminPlayListSearchResultDTO.builder()
                .playlist(m_adminRepository.findRecommendPlayList())
                .build();
    }

    // 추천재생목록 후보를 삭제한 다음, 추천후보목록을 가져온다
    @Override
    @Transactional
    public AdminPlayListSearchResultDTO removeCandiPlayList(Long playlistId) {
        m_adminRepository.removeCandiPlayList(playlistId);
        return AdminPlayListSearchResultDTO.builder()
                .playlist(m_adminRepository.findCandiPlayList())
                .build();
    }

    /*
        - 파라미터로 넘어온 추천재생목록 후보목록을 추천후보목록 테이블에서 삭제
        - 추천재생목록후보를 추천재생목록에 업데이트
        - 추천재생목록 목록을 반환
     */
    @Override
    @Transactional
    public AdminPlayListSearchResultDTO updateRecommendPlayList(List<Long> plList) {
        // 파라미터로 넘어온 추천재생목록 후보목록을 추천후보목록 테이블에서 삭제
        m_adminRepository.deleteFinalCandiPlayList(plList);
        // 추천재생목록 테이블 초기화
        m_adminRepository.deleteAllRecommendPlayList();
        // 추천재생목록후보를 추천재생목록에 업데이트
        m_adminRepository.insertRecommendedPlayList(plList);
        // 추천재생목록 목록을 반환
        List<AdminYoutubePlayListDTO> pl = m_adminRepository.findRecommendPlayList();
        return AdminPlayListSearchResultDTO.builder()
                .playlist(pl)
                .build();
    }
}
