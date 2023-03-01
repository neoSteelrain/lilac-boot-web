package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {

    @Select("SELECT count(id) AS emailCount FROM tbl_member WHERE email = #{email}")
    int findMemberByEmail(String email);
    int saveMember(MemberDTO memberDTO);
    MemberDTO findMember(@Param("email") String email, @Param("password") String password);

    @Select("SELECT id,nickname,email,password,description,region,dtl_region,profile_original,profile_save,grade,reg_date FROM tbl_member")
    List<MemberDTO> findAllMembers();

    int updateMemberInfo(MemberDTO memberDTO);

    @Select("SELECT count(id) AS emailCount FROM tbl_member WHERE nickname = #{nickName}")
    int findMemberByNickName(String nickName);

    int updateMemberProfile(Long memberId, String originalProfileName, String uploadedUrl);
}
