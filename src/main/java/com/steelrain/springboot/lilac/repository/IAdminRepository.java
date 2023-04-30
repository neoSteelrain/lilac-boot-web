package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.AdminBookDTO;
import com.steelrain.springboot.lilac.datamodel.AdminYoutubePlayListDTO;

import java.util.List;

public interface IAdminRepository {
    int insertRecommendedPlayList(List<Long> videoIdList);

    List<Long> selectAllPlayListId();

    List<AdminYoutubePlayListDTO> findAllPlayList(int pageNum, int pageCount);

    int findTotalPlayListCount();

    int findPlayListCountByRange(String fromDate, String toDate);

    List<AdminYoutubePlayListDTO> findPlayListByRange(String start, String end, int pageNum, int pageCount);

    int findLicPlCountByRange(int[] licenseIds, String fromDate, String toDate);

    int findTotalLicPlCount(int[] licenseIds);

    List<AdminYoutubePlayListDTO> findTotalLicPlayList(int[] licenseIds, int pageNum, int pageCount);

    int findTotalSubPlCount(int[] subjectIds);

    List<AdminYoutubePlayListDTO> findTotalSubPlayList(int[] subjectIds, int pageNum, int pageCount);

    int findTotalLicSubCount(int[] licenseIds, int[] subjectIds);

    List<AdminYoutubePlayListDTO> findTotalLicSubPlayList(int[] licenseIds, int[] subjectIds, int pageNum, int pageCount);

    List<AdminYoutubePlayListDTO> findLicPlByRange(int[] licenseIds, String fromDate, String toDate, int pageNum, int pageCount);

    int findSubPlCountByRange(int[] subjectIds, String fromDate, String toDate);

    List<AdminYoutubePlayListDTO> findSubPlByRange(int[] subjectIds, String fromDate, String toDate, int pageNum, int pageCount);

    int findTotalLicSubPlCountByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate);

    List<AdminYoutubePlayListDTO> findLicSubPlByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate, int pageNum, int pageCount);

    int addCandiPlayList(Long playListId);

    List<AdminYoutubePlayListDTO> findCandiPlayList();

    void removeCandiPlayList(Long playlistId);

    void deleteAllRecommendPlayList();

    List<AdminYoutubePlayListDTO> findRecommendPlayList();

    List<Long> findCandidatePlIdList();

    List<Long> findRecommendPlIdList();

    void deleteFinalCandiPlayList(List<Long> plList);

    void removeRecommendPlayList(Long playListId);

    int findTotalBookCount();

    int findBookCountRange(String fromDate, String toDate);

    List<AdminBookDTO> findTotalLicBookList(int[] licenseIds, int pageNum, int pageCount);

    int findTotalLicBookCount(int[] licenseIds);

    List<AdminBookDTO> findTotalBookList(int pageNum, int pageCount);

    List<AdminBookDTO> findTotalSubBookList(int[] subjectIds, int pageNum, int pageCount);

    int findTotalSubBookCount(int[] subjectIds);

    List<AdminBookDTO> findTotalLicSubBookList(int[] licenseIds, int[] subjectIds, int pageNum, int pageCount);

    int findTotalLicSubBookCount(int[] licenseIds, int[] subjectIds);

    List<AdminBookDTO> findLicBookListByRange(int[] licenseIds, String fromDate, String toDate, int pageNum, int pageCount);

    int findLicBookCountByRange(int[] licenseIds, String fromDate, String toDate);

    List<AdminBookDTO> findSubBookListByRange(int[] subjectIds, int pageNum, int pageCount);

    int findSubBookCountByRange(int[] subjectIds, String fromDate, String toDate);

    List<AdminBookDTO> findLicSubBookListByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate, int pageNum, int pageCount);

    int findLicSubBookCountByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate);

    List<AdminBookDTO> findBookListByRange(String fromDate, String toDate, int pageNum, int pageCount);

    int findBookCountByRange(String fromDate, String toDate);

    List<Long> findCandidateBookIdList();

    List<Long> findRecommendBookIdList();

    void addCandiBook(Long bookId);

    List<AdminBookDTO> findCandiBookList();

    void removeCandiBookList(Long bookId);

    void deleteFinalCandiBookList(List<Long> cblList);

    void deleteAllRecommendBookList();

    void insertRecommendedBookList(List<Long> cblList);

    List<AdminBookDTO> findRecommendBookList();

    void deleteRecommendBook(Long bookId);

    List<AdminYoutubePlayListDTO> findPlayListByLike(boolean desc, int[] licenseIds, int[] subjectIds, int pageNum, int pageCount);

    List<AdminYoutubePlayListDTO> findPlayListByViewCount(boolean desc, int[] licenseIds, int[] subjectIds, int pageNum, int pageCount);
}
