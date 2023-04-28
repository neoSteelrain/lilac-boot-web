package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.ADMIN_BOOKLIST_TYPE;
import com.steelrain.springboot.lilac.datamodel.AdminBookSearchResultDTO;

import java.util.List;


public interface IAdminBookService {

    int getTotalBookCount();

    int getTodayBookCount();

    int getWeekBookCount();

    int getMonthBookCount();

    AdminBookSearchResultDTO getAdminBookList(ADMIN_BOOKLIST_TYPE blType, int pageNum, int pageCount, int[] licenseIds, int[] subjectIds);

    void addCandiBook(Long bookId);

    AdminBookSearchResultDTO getCandiBookList();

    AdminBookSearchResultDTO removeCandiBook(Long bookId);

    AdminBookSearchResultDTO updateRecommendBookList(List<Long> cblList);

    AdminBookSearchResultDTO getRecommendBookList();

    AdminBookSearchResultDTO removeRecommendBook(Long bookId);
}
