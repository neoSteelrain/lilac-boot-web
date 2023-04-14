package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;

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
}
