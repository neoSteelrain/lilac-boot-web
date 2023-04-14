package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.AdminYoutubePlayListDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;

import java.util.List;

public interface IAdminRepository {
    int insertRecommendedPlayList(List<Long> videoIdList);

    List<Long> selectAllPlayListId();

    List<AdminYoutubePlayListDTO> findAllPlayList(int pageNum, int pageCount);

    int findTotalPlayListCount();

    int findTodayPlayListCount();

    List<AdminYoutubePlayListDTO> findPlayListByRange(String start, String end, int pageNum, int pageCount);

    int findLicPlCountByRange(int[] licenseIds, String fromDate, String toDate);

    int findWeekPlayListCount();

    int findMonthPlayListCount();

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

    List<Long> findCandidateIdList();

    List<Long> findRecommendIdList();

    void deleteFinalCandiPlayList(List<Long> plList);

    void removeRecommendPlayList(Long playListId);
}
