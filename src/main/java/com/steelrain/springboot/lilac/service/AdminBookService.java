package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.DateUtils;
import com.steelrain.springboot.lilac.common.PeriodDate;
import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.repository.IAdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AdminBookService implements IAdminBookService{

    private final IAdminRepository m_adminRepository;
    private final Map<ADMIN_BOOKLIST_TYPE, IBookListFinder> m_plFinderMap;


    public AdminBookService(IAdminRepository repository){
        this.m_adminRepository = repository;

        m_plFinderMap = new HashMap<>(ADMIN_BOOKLIST_TYPE.values().length);
        m_plFinderMap.put(ADMIN_BOOKLIST_TYPE.ALL, new AllBookListFinder());
        m_plFinderMap.put(ADMIN_BOOKLIST_TYPE.TODAY, new TodayBookListFinder());
        m_plFinderMap.put(ADMIN_BOOKLIST_TYPE.WEEK, new WeekBookListFinder());
        m_plFinderMap.put(ADMIN_BOOKLIST_TYPE.MONTH, new MonthBookListFinder());
    }


    @Override
    public AdminBookSearchResultDTO getAdminBookList(ADMIN_BOOKLIST_TYPE blType, int pageNum, int pageCount, int[] licenseIds, int[] subjectIds) {
        return m_plFinderMap.get(blType).getBookList(pageNum, pageCount, licenseIds, subjectIds, m_adminRepository);
    }

    @Override
    public int getTotalBookCount() {
        return m_adminRepository.findTotalBookCount();
    }

    @Override
    public int getTodayBookCount() {
        PeriodDate today = DateUtils.getToday();
        return m_adminRepository.findBookCountRange(today.getFromDate(), today.getToDate());
    }

    @Override
    public int getWeekBookCount() {
        PeriodDate monday = DateUtils.getMondayOfWeek();
        PeriodDate sunday = DateUtils.getSundayOfWeek();
        return m_adminRepository.findBookCountRange(monday.getFromDate(), sunday.getToDate());
    }

    @Override
    public int getMonthBookCount() {
        PeriodDate firstday = DateUtils.getFirstdayOfMonth();
        PeriodDate lastday = DateUtils.getLastdayOfMonth();
        return m_adminRepository.findBookCountRange(firstday.getFromDate(), lastday.getToDate());
    }

    @Override
    @Transactional
    public void addCandiBook(Long bookId) {
        m_adminRepository.addCandiBook(bookId);
    }

    @Override
    public AdminBookSearchResultDTO getCandiBookList() {
        return AdminBookSearchResultDTO.builder()
                .bookList(m_adminRepository.findCandiBookList())
                .build();
    }

    @Override
    public AdminBookSearchResultDTO removeCandiBook(Long bookId) {
        m_adminRepository.removeCandiBookList(bookId);
        return AdminBookSearchResultDTO.builder()
                .bookList(m_adminRepository.findCandiBookList())
                .build();
    }

    @Override
    @Transactional
    public AdminBookSearchResultDTO updateRecommendBookList(List<Long> cblList) {
        // 파라미터로 넘어온 추천도서후보목록을 추천도서후보목록 테이블에서 삭제
        m_adminRepository.deleteFinalCandiBookList(cblList);
        // 추천도서목록 테이블 초기화
        m_adminRepository.deleteAllRecommendBookList();
        // 추천도서목록후보를 추천도서목록에 업데이트
        m_adminRepository.insertRecommendedBookList(cblList);
        List<AdminBookDTO> bl = m_adminRepository.findRecommendBookList();
        return AdminBookSearchResultDTO.builder()
                .bookList(bl)
                .build();
    }

    @Override
    public AdminBookSearchResultDTO getRecommendBookList() {
        return AdminBookSearchResultDTO.builder()
                .bookList(m_adminRepository.findRecommendBookList())
                .build();
    }

    @Override
    public AdminBookSearchResultDTO removeRecommendBook(Long bookId) {
        m_adminRepository.deleteRecommendBook(bookId);
        return AdminBookSearchResultDTO.builder()
                .bookList(m_adminRepository.findRecommendBookList())
                .build();
    }
}
