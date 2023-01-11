package com.steelrain.springboot.lilac.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LicenseMapper {

    @Select("SELECT name FROM tbl_license WHERE code=#{licenseCode}")
    String getLicenseName(int licenseCode);
}
