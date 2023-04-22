package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LicenseBookMapper {

    void saveKakaoBookList(List<KaKaoBookDTO> kaKaoBookList);

    @Select("SELECT id,isbn13,title,contents,url,publish_date,authors,publisher,translators,price,sale_price,thumbnail,status,reg_date,update_date FROM tbl_book WHERE isbn13=#{isbn}")
    KaKaoBookDTO findKakaoBookInfo(@Param("isbn") Long isbn);

    List<KaKaoBookDTO> getRecommendedBookList();
}
