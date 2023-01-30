package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.repository.IMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
public class MemberServiceTests {
    // TODO 테스트 코드 연습
    private IMemberService m_memberService;

    @MockBean
    private IMemberRepository m_memberRepository;

    @BeforeEach
    void setup(){
        m_memberService = new MemberService(m_memberRepository);
    }

    @Test
    public void testRegisterMember(){

        MemberDTO memberDTO1 = new MemberDTO();
        memberDTO1.setNickname("user2");
        memberDTO1.setEmail("user1@user.com");
        memberDTO1.setPassword("1234");
        memberDTO1.setId(2L);

        Mockito.when(m_memberRepository.saveMember(memberDTO1)).thenReturn(0);

        MemberDTO memberDTO2 = new MemberDTO();
        memberDTO2.setNickname("user2");
        memberDTO2.setEmail("user2@user.com");
        memberDTO2.setPassword("1234");

        boolean res = m_memberService.registerMember(memberDTO2);
        assertThat(res);
        log.info("res : " + res);
    }

    @Test
    public void testMemberLogin(){
        MemberDTO memberDTO = m_memberService.loginMember("user1@user.com", "1234");

        assertThat(memberDTO != null);
        log.info(String.format("memberDTO-email : " + memberDTO.getEmail()));
        log.info(String.format("memberDTO-nickname : " + memberDTO.getNickname()));
        log.info(String.format("memberDTO-password : " + memberDTO.getPassword()));
    }
}
