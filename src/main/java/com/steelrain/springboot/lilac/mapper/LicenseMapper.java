package com.steelrain.springboot.lilac.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LicenseMapper {

    @Select("SELECT name FROM tbl_license WHERE code=#{licenseCode}")
    String getLicenseNameByCode(@Param("licenseCode") int licenseCode);

    @Select("SELECT code FROM tbl_license WHERE id=#{licenseId}")
    Integer getLicenseNameById(@Param("licenseId") int licenseId);

    @Select("SELECT schedule_json FROM tbl_license WHERE code=#{licenseCode}")
    String getLicenseScheduleJsonString(@Param("licenseCode") int licenseCode);

    @Update("UPDATE tbl_license SET schedule_json=#{jsonStr} WHERE code=#{licenseCode}")
    int updateLicenseScheduleJsonString(@Param("licenseCode") int licenseCode, @Param("jsonStr") String jsonStr);
}
