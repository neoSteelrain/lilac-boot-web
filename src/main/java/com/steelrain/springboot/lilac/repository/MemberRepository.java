package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.exception.DuplicateLilacMemberException;
import com.steelrain.springboot.lilac.exception.LilacRepositoryException;
import com.steelrain.springboot.lilac.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
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
    public Long saveMember(MemberDTO memberDTO) throws DuplicateLilacMemberException, LilacRepositoryException {
        try{
            int cnt = m_memberMapper.saveMember(memberDTO);
            if(cnt > 0){
                return memberDTO.getId();
            }else{
                return null;
            }
        }catch(DuplicateKeyException de){
            throw new DuplicateLilacMemberException("이미 가입된 회원입니다", de);
        }catch (Exception ex){
            throw new LilacRepositoryException("회원가입 예외발생", ex);
        }
    }

    @Override
    public boolean deleteMember(Long memberId) {
        return m_memberMapper.deleteMember(memberId) > 0;
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
