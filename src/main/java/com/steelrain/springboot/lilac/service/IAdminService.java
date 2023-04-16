package com.steelrain.springboot.lilac.service;

/**
 * 관리자 서비스 인터페이스
 */

import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;


import java.util.List;

public interface IAdminService {

    int getTotalPlayListCount();

    int getTodayPlayListCount();

    int getWeekPlayListCount();

    int getMonthPlayListCount();

    AdminPlayListSearchResultDTO getAllPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds);

    AdminPlayListSearchResultDTO getTodayPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds);

    AdminPlayListSearchResultDTO getWeekPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds);

    AdminPlayListSearchResultDTO getMonthPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds);

    boolean addCandiPlayList(Long playListId);

    AdminPlayListSearchResultDTO getCandiPlayList();

    AdminPlayListSearchResultDTO removeCandiPlayList(Long playlistId);

    AdminPlayListSearchResultDTO updateRecommendPlayList(List<Long> plList);

    AdminPlayListSearchResultDTO getRecommendPlayList();

    AdminPlayListSearchResultDTO removeRecommendPlayList(Long playListId);
}
