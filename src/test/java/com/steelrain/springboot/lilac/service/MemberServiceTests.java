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

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private IMemberService m_memberService;

    @Autowired
    private ILectureNoteService m_lectureNoteService;



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

    @Test
    public void createMembers() {
        for(int i=1 ; i <= 5 ; i++){
            MemberDTO dto = MemberDTO.builder()
                    .nickname(String.format("user%s", i))
                    .email(String.format("user%s@user.com", i))
                    .password("123456yt")
                    .build();
            m_memberService.registerMember(dto);
        }
    }
    
    @Test
    public void 기본강의노트생성확인테스트(){
        List<MemberDTO> members = m_memberService.getAllMembers();

        members.stream().forEach(member -> {
            log.debug("강의노트 확인 : {}", m_lectureNoteService.getLectureListByMember(member.getId()));
        });
    }
}
