package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.LibraryDetailRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LibraryRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseCodeDTO;
import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {

//    @Select("SELECT name,key_word FROM tbl_subject WHERE is_active = 1 AND is_scheduled = 0") // 배치가 완료된 키워드를 가져오려면 스케쥴링값은 1로 바꿔줘야 한다.
//    @Results(id="SubjectCodeMap", value={
//            @Result(property = "name", column = "name"),
//            @Result(property = "keyWord", column = "key_word")
//    })
    @Select("SELECT name,key_word FROM tbl_subject WHERE is_active = 1 AND is_scheduled = 0")
    List<SubjectCodeDTO> getSubjectCodes();

//    @Select("SELECT code,name FROM tbl_lib_region;")
//    @Results(id="LibRegionCodeMap", value={
//            @Result(property = "code", column = "code"),
//            @Result(property = "name", column = "name")
//    })
    @Select("SELECT code,name FROM tbl_lib_region;")
    List<LibraryRegionCodeDTO> getLibRegionCodes();

//    @Select("SELECT code,detail_name FROM tbl_lib_dtl_region WHERE region_code=#{regionCode}")
//    @Results(id="LibDetailRegionCodeMap", value = {
//            @Result(property = "code", column = "code"),
//            @Result(property = "detailName", column = "detail_name")
//    })
    @Select("SELECT code,detail_name FROM tbl_lib_dtl_region WHERE region_code=#{regionCode}")
    List<LibraryDetailRegionCodeDTO> getLibDetailRegionCodes(int regionCode);

//    @Select("SELECT code,region_code,name,detail_name FROM tbl_lib_dtl_region")
//    @Results(id="AllLibDetailRegionCodeMap", value = {
//            @Result(property = "code", column = "code"),
//            @Result(property = "regionCode", column = "region_code"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "detailName", column = "detail_name")
//    })
    @Select("SELECT code,region_code,name,detail_name FROM tbl_lib_dtl_region")
    List<LibraryDetailRegionCodeDTO> getAllLibDetailRegionCodes();

//    @Select("SELECT code,name,key_word FROM tbl_license")
//    @Results(id="LicenseCodeMap", value={
//            @Result(property = "code", column = "code"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "keyWord", column = "key_word")
//    })
    @Select("SELECT code,name,key_word FROM tbl_license")
    List<LicenseCodeDTO> getLicenseCodes();
}
