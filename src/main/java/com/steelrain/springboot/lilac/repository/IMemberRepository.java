package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.exception.DuplicateLilacMemberException;
import com.steelrain.springboot.lilac.exception.LilacRepositoryException;

import java.util.List;

public interface IMemberRepository {
    int findMemberByEmail(String email);
    Long saveMember(MemberDTO memberDTO) throws DuplicateLilacMemberException, LilacRepositoryException;
    boolean deleteMember(Long memberId);
    MemberDTO findMemberByLoginInfo(String email, String password);
    List<MemberDTO> findAllMembers();
    int updateMemberInfo(MemberDTO memberDTO);
    int findMemberByNickName(String nickName);
    int updateMemberProfile(Long memberId, String originalProfileName, String uploadedUrl);
    MemberDTO findMemberInfo(Long memberId);
}
