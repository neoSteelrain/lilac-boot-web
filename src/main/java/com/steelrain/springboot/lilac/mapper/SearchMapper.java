package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.LibraryDetailRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LibraryRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseCodeDTO;
import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SearchMapper {

    @Select("SELECT id,code,name,key_word,key_word_book FROM tbl_subject WHERE is_active = 1 AND is_scheduled = 0") // 배치가 완료된 키워드를 가져오려면 스케쥴링값은 1로 바꿔줘야 한다.
    List<SubjectCodeDTO> getSubjectCodes();

    @Select("SELECT code,name FROM tbl_lib_region;")
    List<LibraryRegionCodeDTO> getLibRegionCodes();

    @Select("SELECT code,detail_name FROM tbl_lib_dtl_region WHERE region_code=#{regionCode}")
    List<LibraryDetailRegionCodeDTO> getLibDetailRegionCodes(@Param("regionCode") int regionCode);

    @Select("SELECT code,region_code,name,detail_name FROM tbl_lib_dtl_region")
    List<LibraryDetailRegionCodeDTO> getAllLibDetailRegionCodes();

    @Select("SELECT id,code,name,key_word FROM tbl_license")
    List<LicenseCodeDTO> getLicenseCodes();
}
