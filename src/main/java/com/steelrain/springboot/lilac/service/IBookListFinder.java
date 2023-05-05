package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.AdminBookSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

/**
 * 관리자 서비스의 도서검색 기능을 추상화한 인터페이스
 */
public interface IBookListFinder {
    AdminBookSearchResultDTO getBookList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository);
}
