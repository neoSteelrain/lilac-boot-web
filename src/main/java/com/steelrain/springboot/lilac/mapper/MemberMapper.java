package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper {

    @Select("SELECT count(email) AS emailCount FROM tbl_member WHERE email = #{email}")
    int findMemberByEmail(String email);
    int saveMember(MemberDTO memberDTO);
    MemberDTO findMember(@Param("email") String email, @Param("password") String password);

    @Delete("DELETE FROM tbl_member WHERE id=#{memberId}")
    int deleteMember(Long memberId);

    @Select("SELECT id,nickname,email,password,description,region,dtl_region,profile_original,profile_save,grade,reg_date FROM tbl_member")
    List<MemberDTO> findAllMembers();

    int updateMemberInfo(MemberDTO memberDTO);

    @Select("SELECT count(nickname) AS emailCount FROM tbl_member WHERE nickname = #{nickName}")
    int findMemberByNickName(String nickName);

    int updateMemberProfile(@Param("memberId")Long memberId, @Param("originalProfileName")String originalProfileName, @Param("uploadedUrl")String uploadedUrl);

    @Select("SELECT id,nickname,email,password,description,profile_original,profile_save,grade,reg_date,region,dtl_region FROM tbl_member WHERE id=#{memberId}")
    MemberDTO findMemberInfo(Long memberId);
}
