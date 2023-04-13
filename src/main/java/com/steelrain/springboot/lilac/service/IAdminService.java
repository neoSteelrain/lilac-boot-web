package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;

public interface IAdminService {

    int getTotalPlayListCount();

    int getTodayPlayListCount();

    int getWeekPlayListCount();

    int getMonthPlayListCount();

    AdminPlayListSearchResultDTO getAllPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds);

    AdminPlayListSearchResultDTO getTodayPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds);

    AdminPlayListSearchResultDTO getWeekPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds);

    AdminPlayListSearchResultDTO getMonthPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds);
}
