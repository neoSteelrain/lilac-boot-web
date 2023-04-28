package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.AdminBookSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

public interface IBookListFinder {
    AdminBookSearchResultDTO getBookList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository);
}
