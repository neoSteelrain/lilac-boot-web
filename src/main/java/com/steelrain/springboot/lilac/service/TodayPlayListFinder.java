package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.DateUtils;
import com.steelrain.springboot.lilac.common.PeriodDate;
import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

/**
 * 오늘 추가된 재생목록 찾기
 */
public class TodayPlayListFinder implements IPlayListFinder{
    @Override
    public AdminPlayListSearchResultDTO getPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository) {
        PlayListFinderTemplate template = new PlayListFinderTemplate(repository);
        PeriodDate today = DateUtils.getToday();
        return template.getPlayListByRange(today.getFromDate(), today.getToDate(), pageNum, pageCount, licenseIds, subjectIds);
    }
}
