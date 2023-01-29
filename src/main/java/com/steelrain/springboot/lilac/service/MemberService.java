package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.repository.IMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {

    private final IMemberRepository m_memberRepository;


    @Override
    public boolean checkDuplicatedEmail(String email) {
        return m_memberRepository.findMemberByEmail(email) > 0;
    }

    @Override
    public boolean registerMember(MemberDTO memeberDTO) {
        return m_memberRepository.saveMember(memeberDTO) > 0;
    }
}