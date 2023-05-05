package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

/**
 * 관리자 서비스의 재생목록 검색기능을 추상화한 인터페이스
 */
public interface IPlayListFinder {
    AdminPlayListSearchResultDTO getPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository);
}
