package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;


/**
 * 기간 조건이 없이 재생목록 찾기
 */
public class AllPlayListFinder implements IPlayListFinder{
    @Override
    public AdminPlayListSearchResultDTO getPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository) {
        PlayListFinderTemplate template = new PlayListFinderTemplate(repository);
        return template.getPlayListByRange(null, null, pageNum, pageCount,licenseIds, subjectIds);
    }
}
