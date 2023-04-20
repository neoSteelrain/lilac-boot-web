package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.exception.DuplicateLilacMemberException;
import com.steelrain.springboot.lilac.exception.LilacRepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private IMemberRepository m_repo;

    @Test
    @DisplayName("회원가입 테스트")
    void saveMember() throws LilacRepositoryException, DuplicateLilacMemberException {
        MemberDTO memberDTO = MemberDTO.builder()
                .nickname("test")
                .email("test@test.com")
                .password("123456yt")
                .grade(2)
                .build();
        Long id = m_repo.saveMember(memberDTO);
        assertThat(Objects.nonNull(id)).isTrue();
        assertThat(id > 0).isTrue();
        log.debug("id : {}", id);
    }

    @Test
    @Transactional
    @DisplayName("회원탈퇴 테스트")
    void deleteMember() throws DuplicateLilacMemberException, LilacRepositoryException {
        MemberDTO memberDTO = MemberDTO.builder()
                .nickname("test")
                .email("test@test.com")
                .password("123456yt")
                .grade(2)
                .build();
        Long id = m_repo.saveMember(memberDTO);
        boolean isDel = m_repo.deleteMember(id);
        assertThat(isDel).isTrue();
    }

    @Test
    @Transactional
    @DisplayName("회원중복가입 예외테스트")
    void testDuplicatedMember(){
        MemberDTO memberDTO = MemberDTO.builder()
                                    .nickname("test")
                                    .email("test@test.com")
                                    .password("123456yt")
                                    .grade(2)
                                    .build();
        try{
            m_repo.saveMember(memberDTO);
            m_repo.saveMember(memberDTO);
        }catch(DuplicateLilacMemberException de){
            log.error("de 정보 : {}", de);
        }catch (LilacRepositoryException le){
            log.error("le 정보 : {}", le);
        }
    }

    @Test
    void updateMemberInfo() {
    }
}