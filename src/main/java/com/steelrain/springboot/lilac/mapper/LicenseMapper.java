package com.steelrain.springboot.lilac.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LicenseMapper {

    @Select("SELECT name FROM tbl_license WHERE code=#{licenseCode}")
    String getLicenseName(int licenseCode);

    @Select("SELECT schedule_json FROM tbl_license WHERE code=#{licenseCode}")
    String getLicenseScheduleJsonString(int licenseCode);

    @Update("UPDATE tbl_license SET schedule_json=#{jsonStr} WHERE code=#{licenseCode}")
    int udpateLicenseScheduleJsonString(int licenseCode, String jsonStr);
}
