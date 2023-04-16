package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.DateUtils;
import com.steelrain.springboot.lilac.common.PagingUtils;
import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.AdminYoutubePlayListDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 관리자 서비스
 * - 관리자의 비즈니스 로직을 구현
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {

    private final IAdminRepository m_adminRepository;


    @Override
    public AdminPlayListSearchResultDTO getAllPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds) {
        List<AdminYoutubePlayListDTO> plList = null;
        if((licenseIds != null && licenseIds.length > 0) && (subjectIds == null)){
            // 자격증 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findTotalLicPlayList(licenseIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalLicPlCount(licenseIds), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        if((subjectIds != null && subjectIds.length > 0) && (licenseIds == null)){
            // 키워드 재생목록
            plList  = checkRecommend(checkCandidate(m_adminRepository.findTotalSubPlayList(subjectIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalSubPlCount(subjectIds), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        if((licenseIds != null && subjectIds != null) && (licenseIds.length > 0 && subjectIds.length > 0)){
            // 자격증+키워드 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findTotalLicSubPlayList(licenseIds, subjectIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalLicSubCount(licenseIds, subjectIds), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        // 아무조건도 없다면 모든 재생목록
        plList = checkRecommend(checkCandidate(m_adminRepository.findAllPlayList(PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
        return AdminPlayListSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalPlayListCount(), pageNum, pageCount))
                .playlist(plList)
                .build();
    }
    @Override
    public AdminPlayListSearchResultDTO getTodayPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds) {
        List<AdminYoutubePlayListDTO> plList = null;
        if((licenseIds != null && licenseIds.length > 0) && (subjectIds == null)){
            // 자격증 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findLicPlByRange(licenseIds,
                                                                    DateUtils.getYesterdayString(),
                                                                    DateUtils.getTodayDateString(),
                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findLicPlCountByRange(licenseIds, DateUtils.getYesterdayString(), DateUtils.getTodayDateString()), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        if((subjectIds != null && subjectIds.length > 0) && (licenseIds == null)){
            // 키워드 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findSubPlByRange(subjectIds,
                                                                    DateUtils.getYesterdayString(),
                                                                    DateUtils.getTodayDateString(),
                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findSubPlCountByRange(subjectIds, DateUtils.getYesterdayString(), DateUtils.getTodayDateString()), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        if((licenseIds != null && subjectIds != null) && (licenseIds.length > 0 && subjectIds.length > 0)){
            // 자격증+키워드 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findLicSubPlByRange(licenseIds,
                                                                    subjectIds,
                                                                    DateUtils.getYesterdayString(),
                                                                    DateUtils.getTodayDateString(),
                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalLicSubPlCountByRange(licenseIds, subjectIds, DateUtils.getYesterdayString(), DateUtils.getTodayDateString()), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        // 아무조건도 없다면 오늘추가된 모든재생목록
        plList = checkRecommend(checkCandidate(m_adminRepository.findPlayListByRange(DateUtils.getYesterdayString(),
                                                                                    DateUtils.getTodayDateString(),
                                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
        return AdminPlayListSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTodayPlayListCount(), pageNum, pageCount))
                .playlist(plList)
                .build();
    }

    @Override
    public AdminPlayListSearchResultDTO getWeekPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds) {
        List<AdminYoutubePlayListDTO> plList = null;
        if((licenseIds != null && licenseIds.length > 0) && (subjectIds == null)){
            // 자격증 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findLicPlByRange(licenseIds,
                                                                                    DateUtils.getMondayOfWeekString(),
                                                                                    DateUtils.getSundayOfWeekString(),
                                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findLicPlCountByRange(licenseIds, DateUtils.getMondayOfWeekString(), DateUtils.getSundayOfWeekString()), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        if((subjectIds != null && subjectIds.length > 0) && (licenseIds == null)){
            // 키워드 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findSubPlByRange(subjectIds,
                                                                                    DateUtils.getMondayOfWeekString(),
                                                                                    DateUtils.getSundayOfWeekString(),
                                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findSubPlCountByRange(subjectIds, DateUtils.getMondayOfWeekString(), DateUtils.getSundayOfWeekString()), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        if((licenseIds != null && subjectIds != null) && (licenseIds.length > 0 && subjectIds.length > 0)){
            // 자격증+키워드 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findLicSubPlByRange(licenseIds,
                                                                                        subjectIds,
                                                                                        DateUtils.getMondayOfWeekString(),
                                                                                        DateUtils.getSundayOfWeekString(),
                                                                                        PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalLicSubPlCountByRange(licenseIds, subjectIds, DateUtils.getMondayOfWeekString(), DateUtils.getSundayOfWeekString()), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        plList = checkRecommend(checkCandidate(m_adminRepository.findPlayListByRange(DateUtils.getMondayOfWeekString(),
                                                                                    DateUtils.getSundayOfWeekString(),
                                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
        return AdminPlayListSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findWeekPlayListCount(), pageNum, pageCount))
                .playlist(plList)
                .build();
    }

    @Override
    public AdminPlayListSearchResultDTO getMonthPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds) {
        List<AdminYoutubePlayListDTO> plList = null;
        if((licenseIds != null && licenseIds.length > 0) && (subjectIds == null)){
            // 자격증 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findLicPlByRange(licenseIds,
                                                                                    DateUtils.getMondayOfWeekString(),
                                                                                    DateUtils.getSundayOfWeekString(),
                                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findLicPlCountByRange(licenseIds, DateUtils.getFirstdayOfMonth(), DateUtils.getLastdayOfMonth()), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        if((subjectIds != null && subjectIds.length > 0) && (licenseIds == null)){
            // 키워드 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findSubPlByRange(subjectIds,
                                                                                    DateUtils.getFirstdayOfMonth(),
                                                                                    DateUtils.getLastdayOfMonth(),
                                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findSubPlCountByRange(subjectIds, DateUtils.getFirstdayOfMonth(), DateUtils.getLastdayOfMonth()), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        if((licenseIds != null && subjectIds != null) && (licenseIds.length > 0 && subjectIds.length > 0)){
            // 자격증+키워드 재생목록
            plList = checkRecommend(checkCandidate(m_adminRepository.findLicSubPlByRange(licenseIds,
                                                                                        subjectIds,
                                                                                        DateUtils.getFirstdayOfMonth(),
                                                                                        DateUtils.getLastdayOfMonth(),
                                                                                        PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalLicSubPlCountByRange(licenseIds, subjectIds, DateUtils.getFirstdayOfMonth(), DateUtils.getLastdayOfMonth()), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        plList = checkRecommend(checkCandidate(m_adminRepository.findPlayListByRange(DateUtils.getFirstdayOfMonth(),
                                                                                    DateUtils.getLastdayOfMonth(),
                                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
        return AdminPlayListSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findMonthPlayListCount(), pageNum, pageCount))
                .playlist(plList)
                .build();
    }

    // 재생목록이 추천재생목록 후보에 속해있는지 검사하고, 속해있다면 isCandidate 를 true로 설정한다.
    private List<AdminYoutubePlayListDTO> checkCandidate(List<AdminYoutubePlayListDTO> plList){
        List<Long> candiIdList = m_adminRepository.findCandidateIdList();
        for(AdminYoutubePlayListDTO pl : plList){
            for(Long cId : candiIdList){
                if(pl.getId().equals(cId)){
                    pl.setCandidate(true);
                }
            }
        }
        return plList;
    }

    // 재생목록이 추천재생목록에 속해있는지 검사하고, 속해있다면 isRecommend 를 true로 설정한다.
    private List<AdminYoutubePlayListDTO> checkRecommend(List<AdminYoutubePlayListDTO> plList){
        List<Long> recommedIdList = m_adminRepository.findRecommendIdList();
        for(AdminYoutubePlayListDTO pl : plList){
            for(Long rId : recommedIdList){
                if(pl.getId().equals(rId)){
                    pl.setRecommend(true);
                }
            }
        }
        return plList;
    }

    @Override
    public int getTotalPlayListCount() {
        return m_adminRepository.findTotalPlayListCount();
    }

    @Override
    public int getTodayPlayListCount() {
        return m_adminRepository.findTodayPlayListCount();
    }

    @Override
    public int getWeekPlayListCount() {
        return m_adminRepository.findWeekPlayListCount();
    }

    @Override
    public int getMonthPlayListCount() {
        return m_adminRepository.findMonthPlayListCount();
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

    @Override
    public AdminPlayListSearchResultDTO getRecommendPlayList() {
        return AdminPlayListSearchResultDTO.builder()
                .playlist(m_adminRepository.findRecommendPlayList())
                .build();
    }

    @Override
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
