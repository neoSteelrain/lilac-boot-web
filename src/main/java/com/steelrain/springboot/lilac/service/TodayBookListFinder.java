package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.DateUtils;
import com.steelrain.springboot.lilac.common.PeriodDate;
import com.steelrain.springboot.lilac.datamodel.AdminBookSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

public class TodayBookListFinder implements IBookListFinder{
    @Override
    public AdminBookSearchResultDTO getBookList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository) {
        BookListFinderTemplate template = new BookListFinderTemplate(repository);
        PeriodDate today = DateUtils.getToday();
        return template.getBookListByRange(today.getFromDate(), today.getToDate(), pageNum, pageCount, licenseIds, subjectIds);
    }
}
