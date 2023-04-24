package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.DateUtils;
import com.steelrain.springboot.lilac.common.PeriodDate;
import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

/**
 * 1달간 추가된 재생목록 찾기
 */
public class MonthPlayListFinder implements IPlayListFinder{
    @Override
    public AdminPlayListSearchResultDTO getPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository) {
        PlayListFinderTemplate template = new PlayListFinderTemplate(repository);
        PeriodDate firstDay = DateUtils.getFirstdayOfMonth();
        PeriodDate lastDay = DateUtils.getLastdayOfMonth();
        return template.getPlayListByRange(firstDay.getFromDate(), lastDay.getToDate(), pageNum, pageCount, licenseIds, subjectIds);
    }
}
