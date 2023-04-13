package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;

import java.util.List;

public interface IAdminRepository {
    int insertRecommendedPlayList(List<Long> videoIdList);

    List<Long> selectAllPlayListId();

    List<YoutubePlayListDTO> findAllPlayList(int pageNum, int pageCount);

    int findTotalPlayListCount();

    int findTodayPlayListCount();

    List<YoutubePlayListDTO> findPlayListByRange(String start, String end, int pageNum, int pageCount);

    int findLicPlCountByRange(int[] licenseIds, String fromDate, String toDate);

    int findWeekPlayListCount();

    int findMonthPlayListCount();

    int findTotalLicPlCount(int[] licenseIds);

    List<YoutubePlayListDTO> findTotalLicPlayList(int[] licenseIds, int pageNum, int pageCount);

    int findTotalSubPlCount(int[] subjectIds);

    List<YoutubePlayListDTO> findTotalSubPlayList(int[] subjectIds, int pageNum, int pageCount);

    int findTotalLicSubCount(int[] licenseIds, int[] subjectIds);

    List<YoutubePlayListDTO> findTotalLicSubPlayList(int[] licenseIds, int[] subjectIds, int pageNum, int pageCount);

    List<YoutubePlayListDTO> findLicPlByRange(int[] licenseIds, String fromDate, String toDate, int pageNum, int pageCount);

    int findSubPlCountByRange(int[] subjectIds, String fromDate, String toDate);

    List<YoutubePlayListDTO> findSubPlByRange(int[] subjectIds, String fromDate, String toDate, int pageNum, int pageCount);

    int findTotalLicSubPlCountByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate);

    List<YoutubePlayListDTO> findLicSubPlByRange(int[] licenseIds, int[] subjectIds, String fromDate, String toDate, int pageNum, int pageCount);
}
