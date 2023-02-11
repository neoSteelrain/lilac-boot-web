package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LicenseBookMapper {

    void saveKakaoBookList(List<KaKaoBookDTO> kaKaoBookList);
}
