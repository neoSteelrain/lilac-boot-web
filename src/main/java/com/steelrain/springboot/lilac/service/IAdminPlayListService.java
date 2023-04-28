package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.ADMIN_PLAYLIST_TYPE;
import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import java.util.List;

/**
 * 관리자 서비스 인터페이스
 */
public interface IAdminPlayListService {

    AdminPlayListSearchResultDTO getAdminPlayList(ADMIN_PLAYLIST_TYPE type, int pageNum, int pageCount, int[] licenseIds, int[] subjectIds);

    int getTotalPlayListCount();

    int getTodayPlayListCount();

    int getWeekPlayListCount();

    int getMonthPlayListCount();

    boolean addCandiPlayList(Long playListId);

    AdminPlayListSearchResultDTO getCandiPlayList();

    AdminPlayListSearchResultDTO removeCandiPlayList(Long playlistId);

    AdminPlayListSearchResultDTO updateRecommendPlayList(List<Long> plList);

    AdminPlayListSearchResultDTO getRecommendPlayList();

    AdminPlayListSearchResultDTO removeRecommendPlayList(Long playListId);
}
