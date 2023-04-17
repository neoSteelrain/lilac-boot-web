package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository implements IMemberRepository{

    private final MemberMapper m_memberMapper;


    @Override
    public MemberDTO findMemberInfo(Long memberId) {
        return m_memberMapper.findMemberInfo(memberId);
    }

    @Override
    public int findMemberByEmail(String email) {
        return m_memberMapper.findMemberByEmail(email);
    }

    @Override
    public int saveMember(MemberDTO memberDTO) {
        return m_memberMapper.saveMember(memberDTO);
    }

    @Override
    public MemberDTO findMemberByLoginInfo(String email, String password) {
        return m_memberMapper.findMember(email, password);
    }

    @Override
    public List<MemberDTO> findAllMembers() {
        return m_memberMapper.findAllMembers();
    }

    @Override
    public int updateMemberInfo(MemberDTO memberDTO) {
        return m_memberMapper.updateMemberInfo(memberDTO);
    }

    @Override
    public int findMemberByNickName(String nickName) {
        return m_memberMapper.findMemberByNickName(nickName);
    }

    @Override
    public int updateMemberProfile(Long memberId, String originalProfileName, String uploadedUrl) {
        return m_memberMapper.updateMemberProfile(memberId, originalProfileName, uploadedUrl);
    }
}
