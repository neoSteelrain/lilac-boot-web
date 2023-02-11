package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;

import java.util.List;

public interface IMemberService {
    boolean checkDuplicatedEmail(String email);
    boolean registerMember(MemberDTO memberDTO);

    MemberDTO loginMember(String email, String password);

    List<MemberDTO> getAllMembers();

    void updateMemberInfo(MemberDTO memberDTO);
}
