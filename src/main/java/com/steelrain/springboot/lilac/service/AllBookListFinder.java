package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.AdminBookSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

public class AllBookListFinder implements IBookListFinder{
    @Override
    public AdminBookSearchResultDTO getBookList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository) {
        BookListFinderTemplate template = new BookListFinderTemplate(repository);
        return template.getBookListByRange(null, null, pageNum, pageCount, licenseIds, subjectIds);
    }
}
