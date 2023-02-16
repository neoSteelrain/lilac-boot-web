package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.event.MemberRegistrationEvent;
import com.steelrain.springboot.lilac.repository.IMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {

    private final IMemberRepository m_memberRepository;
    private final ApplicationEventPublisher m_appEventPublisher;


    @Override
    public boolean checkDuplicatedEmail(String email) {
        return m_memberRepository.findMemberByEmail(email) > 0;
    }

    @Override
    public boolean checkDuplicatedNickName(String nickName) {
        return m_memberRepository.findMemberByNickName(nickName) > 0 ;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean registerMember(MemberDTO memberDTO) {
        boolean isRegistered = m_memberRepository.saveMember(memberDTO) > 0;
        MemberRegistrationEvent registrationEvent = MemberRegistrationEvent.builder()
                .memberNickname(memberDTO.getNickname())
                .memberId(memberDTO.getId())
                .build();
        m_appEventPublisher.publishEvent(registrationEvent);
        return isRegistered;
    }

    @Override
    public MemberDTO loginMember(String email, String password) {
        return m_memberRepository.findMemberByLoginInfo(email, password);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        return m_memberRepository.findAllMembers();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMemberInfo(MemberDTO memberDTO) {
        m_memberRepository.updateMemberInfo(memberDTO);
    }
}