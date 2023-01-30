package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberSericeTests {

    @Autowired
    private IMemberService m_memberService;

    @Test
    //@Transactional
    public void testRegisterMember(){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setNickname("user1");
        memberDTO.setEmail("user1@user.com");
        memberDTO.setPassword("1234");

        boolean res = m_memberService.registerMember(memberDTO);
        assertThat(res);
    }

    @Test
    public void testMemberLogin(){
        MemberDTO memberDTO = m_memberService.loginMember("user1@user.com", "1234");

        assertThat(memberDTO != null);
    }
}
