package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

public interface IPlayListFinder {
    AdminPlayListSearchResultDTO getPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository);
}
