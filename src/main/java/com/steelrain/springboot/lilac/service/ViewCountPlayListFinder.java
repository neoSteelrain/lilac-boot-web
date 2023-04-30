package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.PagingUtils;
import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.AdminYoutubePlayListDTO;
import com.steelrain.springboot.lilac.repository.IAdminRepository;

import java.util.List;

public class ViewCountPlayListFinder implements IPlayListFinder{
    private final boolean m_isDesc;

    public ViewCountPlayListFinder(boolean isDesc){
        this.m_isDesc = isDesc;
    }
    @Override
    public AdminPlayListSearchResultDTO getPlayList(int pageNum, int pageCount, int[] licenseIds, int[] subjectIds, IAdminRepository repository) {
        List<AdminYoutubePlayListDTO> pl = repository.findPlayListByViewCount(m_isDesc, licenseIds, subjectIds, pageNum, pageCount);
        return AdminPlayListSearchResultDTO.builder()
                .pageDTO(PagingUtils.createPagingInfo(repository.findTotalPlayListCount(), pageNum, pageCount))
                .playlist(pl)
                .build();
    }
}
