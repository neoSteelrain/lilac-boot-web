package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;

public interface IMemberRepository {
    int findMemberByEmail(String email);
    int saveMember(MemberDTO memberDTO);

    MemberDTO loginMember(String email, String password);
}
