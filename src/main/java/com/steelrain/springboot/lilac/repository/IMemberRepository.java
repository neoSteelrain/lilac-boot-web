package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;

import java.util.List;

public interface IMemberRepository {
    int findMemberByEmail(String email);
    int saveMember(MemberDTO memberDTO);
    MemberDTO findMemberByLoginInfo(String email, String password);
    List<MemberDTO> findAllMembers();
    int updateMemberInfo(MemberDTO memberDTO);
    int findMemberByNickName(String nickName);
    int updateMemberProfile(Long memberId, String originalProfileName, String uploadedUrl);
}
