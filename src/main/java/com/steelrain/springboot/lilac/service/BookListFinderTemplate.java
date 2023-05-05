package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.PagingUtils;
import com.steelrain.springboot.lilac.datamodel.AdminBookDTO;
import com.steelrain.springboot.lilac.datamodel.AdminBookSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 *
 */
@RequiredArgsConstructor
public class BookListFinderTemplate {

    private final IAdminRepository m_adminRepository;


    public AdminBookSearchResultDTO getBookListByRange(String fromDate, String toDate, int pageNum, int pageCount, int[] licenseIds, int[] subjectIds){
        boolean isSelectAll = !(StringUtils.hasText(fromDate) || StringUtils.hasText(toDate));
        List<AdminBookDTO> bookList = null;
        if((licenseIds != null && licenseIds.length > 0) && (subjectIds == null)){
            // 자격증 도서목록
            bookList = checkRecommend(checkCandidate(isSelectAll ? m_adminRepository.findTotalLicBookList(licenseIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount) :
                                                                   m_adminRepository.findLicBookListByRange(licenseIds, fromDate, toDate, PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminBookSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(isSelectAll ? m_adminRepository.findTotalLicBookCount(licenseIds) :
                                                                        m_adminRepository.findLicBookCountByRange(licenseIds, fromDate, toDate) ,pageNum, pageCount))
                    .bookList(bookList)
                    .build();
        }
        if((subjectIds != null && subjectIds.length > 0) && (licenseIds == null)){
            // 키워드 도서목록
            bookList = checkRecommend(checkCandidate(isSelectAll ? m_adminRepository.findTotalSubBookList(subjectIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount) :
                                                                   m_adminRepository.findSubBookListByRange(subjectIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminBookSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(isSelectAll ? m_adminRepository.findTotalSubBookCount(subjectIds) :
                                                                        m_adminRepository.findSubBookCountByRange(subjectIds, fromDate, toDate), pageNum, pageCount))
                    .bookList(bookList)
                    .build();
        }
        if((licenseIds != null && subjectIds != null) && (licenseIds.length > 0 && subjectIds.length > 0)){
            // 자격증+키워드 도서목록
            bookList = checkRecommend(checkCandidate(isSelectAll ? m_adminRepository.findTotalLicSubBookList(licenseIds, subjectIds, PagingUtils.calcStartPage(pageNum, pageCount), pageCount) :
                                                                   m_adminRepository.findLicSubBookListByRange(licenseIds, subjectIds, fromDate, toDate, PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
            return AdminBookSearchResultDTO.builder()
                    .pageDTO(PagingUtils.createPagingInfo(isSelectAll ? m_adminRepository.findTotalLicSubBookCount(licenseIds, subjectIds) :
                                                                        m_adminRepository.findLicSubBookCountByRange(licenseIds, subjectIds, fromDate, toDate), pageNum, pageCount))
                    .bookList(bookList)
                    .build();
        }
        // 모든도서목록
        bookList = checkRecommend(checkCandidate(isSelectAll ? m_adminRepository.findTotalBookList(PagingUtils.calcStartPage(pageNum, pageCount), pageCount) :
                                                               m_adminRepository.findBookListByRange(fromDate, toDate, PagingUtils.calcStartPage(pageNum, pageCount), pageCount)));
        return AdminBookSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(isSelectAll ? m_adminRepository.findTotalBookCount() :
                                                                    m_adminRepository.findBookCountByRange(fromDate, toDate), pageNum, pageCount))
                .bookList(bookList)
                .build();
    }

    // 재생목록이 추천재생목록 후보에 속해있는지 검사하고, 속해있다면 isCandidate 를 true로 설정한다.
    private List<AdminBookDTO> checkCandidate(List<AdminBookDTO> bookList){
        List<Long> candiIdList = m_adminRepository.findCandidateBookIdList();
        for(AdminBookDTO pl : bookList){
            for(Long cId : candiIdList){
                if(pl.getId().equals(cId)){
                    pl.setCandidate(true);
                }
            }
        }
        return bookList;
    }

    // 재생목록이 추천재생목록에 속해있는지 검사하고, 속해있다면 isRecommend 를 true로 설정한다.
    private List<AdminBookDTO> checkRecommend(List<AdminBookDTO> bookList){
        List<Long> recommedIdList = m_adminRepository.findRecommendBookIdList();
        for(AdminBookDTO pl : bookList){
            for(Long rId : recommedIdList){
                if(pl.getId().equals(rId)){
                    pl.setRecommend(true);
                }
            }
        }
        return bookList;
    }
}
