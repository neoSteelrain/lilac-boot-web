package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.repository.IMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private IMemberService m_memberService;


    @Test
    @DisplayName("회원가입 테스트")
    public void testRegisterMember(){

        MemberDTO memberDTO1 = MemberDTO.builder()
                        .nickname("user2")
                        .email("user2@user.com")
                        .password("123456yt")
                        .build();

        boolean res = m_memberService.registerMember(memberDTO1);
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
