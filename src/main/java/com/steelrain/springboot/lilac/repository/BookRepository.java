package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedBookListDTO;
import com.steelrain.springboot.lilac.mapper.LicenseBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final LicenseBookMapper m_licenseBookMapper;


    public void saveKakaoBookList(List<KaKaoBookDTO> kaKaoBookList) {
        // ISBN이 있으면 update 없으면 insert
        m_licenseBookMapper.saveKakaoBookList(kaKaoBookList);
    }

    public KaKaoBookDTO findKaKaoBookInfo(Long isbn){
        return m_licenseBookMapper.findKakaoBookInfo(isbn);
    }

    public List<KaKaoBookDTO> getRecommendedBookList() {
        return m_licenseBookMapper.getRecommendedBookList();
    }
}
