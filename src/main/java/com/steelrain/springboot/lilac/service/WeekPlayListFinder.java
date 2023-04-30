package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.DateUtils;
import com.steelrain.springboot.lilac.common.PeriodDate;
import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

/**
 * 1일주일간 추가된 재생목록 찾기
 */
public class WeekPlayListFinder implements IPlayListFinder{
    @Override
    public AdminPlayListSearchResultDTO getPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository) {
        PlayListPeriodFinderTemplate template = new PlayListPeriodFinderTemplate(repository);
        PeriodDate monday = DateUtils.getMondayOfWeek();
        PeriodDate sunday = DateUtils.getSundayOfWeek();
        return template.getPlayListByRange(monday.getFromDate(), sunday.getToDate(), pageNum, pageCount, licenseIds, subjectIds);
    }
}
