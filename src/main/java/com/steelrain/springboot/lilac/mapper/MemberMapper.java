package com.steelrain.springboot.lilac.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Select("SELECT count(id) AS emailCount FROM tbl_member WHERE email = #{email}")
    int findMemberByEmail(String email);
}
