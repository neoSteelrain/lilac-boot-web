package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.DateUtils;
import com.steelrain.springboot.lilac.common.PagingUtils;
import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 관리자 서비스
 * - 관리자의 비즈니스 로직을 구현
 * - 관리자 서비스는 다른 서비스를 이용하거나 기능을 위임하지 않는다
 * - 기능이 다른 서비스와 유사하거나 겹치더라도 독립적으로 동작하도록 구현한다
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService {

    private final IAdminRepository m_adminRepository;


    @Transactional(rollbackFor = Exception.class)
    public boolean addRecommendedPlayList(List<Long> videoIdList){
        return m_adminRepository.insertRecommendedPlayList(videoIdList) == videoIdList.size();
    }

    public List<Long> getAllPlayListId(){
        return m_adminRepository.selectAllPlayListId();
    }

    @Override
    public AdminPlayListSearchResultDTO getAllPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds) {
        if((licenseIds != null && licenseIds.length > 0) && (subjectIds == null)){
            // 자격증 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalLicPlCount(licenseIds), pageNum, pageCount))
                    .playlist(m_adminRepository.findTotalLicPlayList(licenseIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        if((subjectIds != null && subjectIds.length > 0) && (licenseIds == null)){
            // 키워드 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalSubPlCount(subjectIds), pageNum, pageCount))
                    .playlist(m_adminRepository.findTotalSubPlayList(subjectIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        if((licenseIds != null && subjectIds != null) && (licenseIds.length > 0 && subjectIds.length > 0)){
            // 자격증+키워드 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalLicSubCount(licenseIds, subjectIds), pageNum, pageCount))
                    .playlist(m_adminRepository.findTotalLicSubPlayList(licenseIds, subjectIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        // 아무조건도 없다면 모든 재생목록
        return AdminPlayListSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalPlayListCount(), pageNum, pageCount))
                .playlist(m_adminRepository.findAllPlayList(PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                .build();
    }
    @Override
    public AdminPlayListSearchResultDTO getTodayPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds) {
        if((licenseIds != null && licenseIds.length > 0) && (subjectIds == null)){
            // 자격증 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findLicPlCountByRange(licenseIds, DateUtils.getYesterdayString(), DateUtils.getTodayDateString()), pageNum, pageCount))
                    .playlist(m_adminRepository.findLicPlByRange(licenseIds,
                                                                 DateUtils.getYesterdayString(),
                                                                 DateUtils.getTodayDateString(),
                                                                 PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        if((subjectIds != null && subjectIds.length > 0) && (licenseIds == null)){
            // 키워드 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findSubPlCountByRange(subjectIds, DateUtils.getYesterdayString(), DateUtils.getTodayDateString()), pageNum, pageCount))
                    .playlist(m_adminRepository.findSubPlByRange(subjectIds,
                                                                 DateUtils.getYesterdayString(),
                                                                 DateUtils.getTodayDateString(),
                                                                 PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        if((licenseIds != null && subjectIds != null) && (licenseIds.length > 0 && subjectIds.length > 0)){
            // 자격증+키워드 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalLicSubPlCountByRange(licenseIds, subjectIds, DateUtils.getYesterdayString(), DateUtils.getTodayDateString()), pageNum, pageCount))
                    .playlist(m_adminRepository.findLicSubPlByRange(licenseIds,
                                                                    subjectIds,
                                                                    DateUtils.getYesterdayString(),
                                                                    DateUtils.getTodayDateString(),
                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        // 아무조건도 없다면 오늘추가된 모든재생목록
        return AdminPlayListSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTodayPlayListCount(), pageNum, pageCount))
                .playlist(m_adminRepository.findPlayListByRange(DateUtils.getYesterdayString(),
                                                                DateUtils.getTodayDateString(),
                                                                PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                .build();
    }

    @Override
    public AdminPlayListSearchResultDTO getWeekPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds) {
        if((licenseIds != null && licenseIds.length > 0) && (subjectIds == null)){
            // 자격증 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findLicPlCountByRange(licenseIds, DateUtils.getMondayOfWeekString(), DateUtils.getSundayOfWeekString()), pageNum, pageCount))
                    .playlist(m_adminRepository.findLicPlByRange(licenseIds,
                                                                DateUtils.getMondayOfWeekString(),
                                                                DateUtils.getSundayOfWeekString(),
                                                                PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                                            .build();
        }
        if((subjectIds != null && subjectIds.length > 0) && (licenseIds == null)){
            // 키워드 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findSubPlCountByRange(subjectIds, DateUtils.getMondayOfWeekString(), DateUtils.getSundayOfWeekString()), pageNum, pageCount))
                    .playlist(m_adminRepository.findSubPlByRange(subjectIds,
                                                                DateUtils.getMondayOfWeekString(),
                                                                DateUtils.getSundayOfWeekString(),
                                                                PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        if((licenseIds != null && subjectIds != null) && (licenseIds.length > 0 && subjectIds.length > 0)){
            // 자격증+키워드 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalLicSubPlCountByRange(licenseIds, subjectIds, DateUtils.getMondayOfWeekString(), DateUtils.getSundayOfWeekString()), pageNum, pageCount))
                    .playlist(m_adminRepository.findLicSubPlByRange(licenseIds,
                                                                    subjectIds,
                                                                    DateUtils.getMondayOfWeekString(),
                                                                    DateUtils.getSundayOfWeekString(),
                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        return AdminPlayListSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findWeekPlayListCount(), pageNum, pageCount))
                .playlist(m_adminRepository.findPlayListByRange(DateUtils.getMondayOfWeekString(),
                                                                DateUtils.getSundayOfWeekString(),
                                                                PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                .build();
    }

    @Override
    public AdminPlayListSearchResultDTO getMonthPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds) {
        if((licenseIds != null && licenseIds.length > 0) && (subjectIds == null)){
            // 자격증 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findLicPlCountByRange(licenseIds, DateUtils.getFirstdayOfMonth(), DateUtils.getLastdayOfMonth()), pageNum, pageCount))
                    .playlist(m_adminRepository.findLicPlByRange(licenseIds,
                            DateUtils.getMondayOfWeekString(),
                            DateUtils.getSundayOfWeekString(),
                            PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        if((subjectIds != null && subjectIds.length > 0) && (licenseIds == null)){
            // 키워드 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findSubPlCountByRange(subjectIds, DateUtils.getFirstdayOfMonth(), DateUtils.getLastdayOfMonth()), pageNum, pageCount))
                    .playlist(m_adminRepository.findSubPlByRange(subjectIds,
                            DateUtils.getFirstdayOfMonth(),
                            DateUtils.getLastdayOfMonth(),
                            PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        if((licenseIds != null && subjectIds != null) && (licenseIds.length > 0 && subjectIds.length > 0)){
            // 자격증+키워드 재생목록
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findTotalLicSubPlCountByRange(licenseIds, subjectIds, DateUtils.getFirstdayOfMonth(), DateUtils.getLastdayOfMonth()), pageNum, pageCount))
                    .playlist(m_adminRepository.findLicSubPlByRange(licenseIds,
                            subjectIds,
                            DateUtils.getFirstdayOfMonth(),
                            DateUtils.getLastdayOfMonth(),
                            PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                    .build();
        }
        return AdminPlayListSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(m_adminRepository.findMonthPlayListCount(), pageNum, pageCount))
                .playlist(m_adminRepository.findPlayListByRange(DateUtils.getFirstdayOfMonth(),
                                                                DateUtils.getLastdayOfMonth(),
                                                                PagingUtils.calcStartPage(pageNum, pageCount), pageCount))
                .build();
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
}
