package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.DateUtils;
import com.steelrain.springboot.lilac.common.PeriodDate;
import com.steelrain.springboot.lilac.datamodel.AdminBookSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

public class MonthBookListFinder implements IBookListFinder{
    @Override
    public AdminBookSearchResultDTO getBookList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository) {
        BookListFinderTemplate template = new BookListFinderTemplate(repository);
        PeriodDate firstday = DateUtils.getFirstdayOfMonth();
        PeriodDate lastday = DateUtils.getLastdayOfMonth();
        return template.getBookListByRange(firstday.getFromDate(), lastday.getToDate(), pageNum, pageCount, licenseIds, subjectIds);
    }
}
