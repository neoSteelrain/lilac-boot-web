package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.PagingUtils;
import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.AdminYoutubePlayListDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 관리자 재생목록 보기에 필요한 재생목록을 조회하고 반환하는 기능을 구현한 템플릿 클래스
 */
@RequiredArgsConstructor
public class PlayListFinderTemplate {

    private final IAdminRepository m_adminRepository;


    /**
     * 일정 기간동안 등록된 카테고리별 재생목록을 찾아온다
     * 페이징 파라미터를 입력받아 페이징 처리까지 한다
     * @param fromDate 시작 날짜, 등록일 기준 예) 2024-1-01 00:00:00 문자열 형식이어야 한다
     * @param toDate 끝 날짜, 등록일 기준 예) 2024-01-02 59:59:59 문자열 형식이어야 한다
     * @param pageNum 페이징 시작 페이지
     * @param pageCount 한 페이지에 들어갈 항목 개수
     * @param licenseIds 카테고리로 필터링할 자격증 종류, 코드가 아니고 자격증 DB 테이블의 ID 값
     * @param subjectIds 카테고리로 필터링할 주제키워드 종류, 코드가 아니고 주제키워드 DB 테이블의 ID 값
     * @return 기간, 카테고리로 필터링, 페이징 된 재생목록정보
     */
    public AdminPlayListSearchResultDTO getPlayListByRange(String fromDate, String toDate, int pageNum, int pageCount, int[] licenseIds, int[] subjectIds){
        // 등록일 기준으로 검색할지 결정하는 플래그, 범위검색,전체검색을 한번에 처리하기 위해 선언한다
        boolean isSelectAll = !(StringUtils.hasText(fromDate) || StringUtils.hasText(toDate));
        List<AdminYoutubePlayListDTO> plList = null;
        if((licenseIds != null && licenseIds.length > 0) && (subjectIds == null)){
            // 자격증 재생목록
            plList = checkRecommend(checkCandidate(isSelectAll ? m_adminRepository.findTotalLicPlayList(licenseIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount) :
                                                                 m_adminRepository.findLicPlByRange(licenseIds,
                                                                                                    fromDate,
                                                                                                    toDate,
                                                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(isSelectAll ? m_adminRepository.findTotalLicPlCount(licenseIds) :
                                                                        m_adminRepository.findLicPlCountByRange(licenseIds, fromDate, toDate), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        if((subjectIds != null && subjectIds.length > 0) && (licenseIds == null)){
            // 키워드 재생목록
            plList = checkRecommend(checkCandidate(isSelectAll ? m_adminRepository.findTotalSubPlayList(subjectIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount) :
                                                                 m_adminRepository.findSubPlByRange(subjectIds,
                                                                                                    fromDate,
                                                                                                    toDate,
                                                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(isSelectAll ? m_adminRepository.findTotalSubPlCount(subjectIds) :
                                                                        m_adminRepository.findSubPlCountByRange(subjectIds, fromDate, toDate), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        if((licenseIds != null && subjectIds != null) && (licenseIds.length > 0 && subjectIds.length > 0)){
            // 자격증+키워드 재생목록
            plList = checkRecommend(checkCandidate(isSelectAll ? m_adminRepository.findTotalLicSubPlayList(licenseIds, subjectIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount) :
                                                                 m_adminRepository.findLicSubPlByRange(licenseIds,
                                                                                                        subjectIds,
                                                                                                        fromDate,
                                                                                                        toDate,
                                                                                                        PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminPlayListSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(isSelectAll ? m_adminRepository.findTotalLicSubCount(licenseIds, subjectIds) :
                                                                        m_adminRepository.findTotalLicSubPlCountByRange(licenseIds, subjectIds, fromDate, toDate), pageNum, pageCount))
                    .playlist(plList)
                    .build();
        }
        plList = checkRecommend(checkCandidate(isSelectAll ? m_adminRepository.findAllPlayList(PagingUtils.calcStartPage(pageNum, pageCount), pageCount) :
                                                             m_adminRepository.findPlayListByRange(fromDate,
                                                                                                    toDate,
                                                                                                    PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
        return AdminPlayListSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(isSelectAll ? m_adminRepository.findTotalPlayListCount() :
                                                                    m_adminRepository.findPlayListCountByRange(fromDate, toDate), pageNum, pageCount))
                .playlist(plList)
                .build();
    }

    // 재생목록이 추천재생목록 후보에 속해있는지 검사하고, 속해있다면 isCandidate 를 true로 설정한다.
    private List<AdminYoutubePlayListDTO> checkCandidate(List<AdminYoutubePlayListDTO> plList){
        List<Long> candiIdList = m_adminRepository.findCandidatePlIdList();
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
        List<Long> recommedIdList = m_adminRepository.findRecommendPlIdList();
        for(AdminYoutubePlayListDTO pl : plList){
            for(Long rId : recommedIdList){
                if(pl.getId().equals(rId)){
                    pl.setRecommend(true);
                }
            }
        }
        return plList;
    }
}
