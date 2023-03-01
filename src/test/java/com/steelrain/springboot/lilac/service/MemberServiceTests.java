package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.repository.IMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.swing.*;

import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
    @Rollback()
    public void 회원가입닉네임중복테스트(){
        String testNickname = "user2";
        MemberDTO memberDTO1 = MemberDTO.builder()
                .nickname(testNickname)
                .email("user2@user.com")
                .password("123456yt")
                .grade(2)
                .build();

        boolean isRegistered = m_memberService.registerMember(memberDTO1);
        assertThat(isRegistered).isTrue();

        boolean isDuplicatedNickname = m_memberService.checkDuplicatedNickName(testNickname);
        assertThat(isDuplicatedNickname).isTrue();
    }

    @Test
    @Rollback
    public void 회원가입중복예외테스트(){
        String testNickname = "user2";
        MemberDTO memberDTO1 = MemberDTO.builder()
                .nickname(testNickname)
                .email("user2@user.com")
                .password("123456yt")
                .grade(2)
                .build();

        m_memberService.registerMember(memberDTO1);
        //assertThatThrownBy( () -> m_memberService.registerMember(memberDTO1)).isInstanceOf(DuplicateKeyException.class).hasMessageContaining(testNickname);
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
                    .grade(2)
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

    @Test
    //@Transactional
    public void 회원프로필변경테스트(){
        String TEST_FILE_PATH = "C:\\large_1769431.png";
        Long memberId = 2L;
        File file = new File(TEST_FILE_PATH);
        try {
            FileItem fileItem = new DiskFileItem(file.getName(), Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = fileItem.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

            boolean isUpdated = m_memberService.updateMemberProfile(multipartFile, memberId);
            assertThat(isUpdated).isTrue();
        }catch(IOException ioe){
            log.debug("회원프로필변경 테스트 예외 : {}", ioe);
        }
    }
}
