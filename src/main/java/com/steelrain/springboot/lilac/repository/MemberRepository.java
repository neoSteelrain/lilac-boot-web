package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository implements IMemberRepository{

    private final MemberMapper m_memberMapper;

    @Override
    public int findMemberByEmail(String email) {
        return m_memberMapper.findMemberByEmail(email);
    }

    @Override
    public int saveMember(MemberDTO memberDTO) {
        return 0;
    }
}
