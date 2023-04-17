package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.datamodel.view.MemberProfileEditDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 회원서비스 인터페이스
 */
public interface IMemberService {
    boolean checkDuplicatedEmail(String email);
    boolean checkDuplicatedNickName(String nickName);
    boolean registerMember(MemberDTO memberDTO);
    MemberDTO loginMember(String email, String password);
    List<MemberDTO> getAllMembers();
    void updateMemberInfo(MemberDTO memberDTO, MemberProfileEditDTO editDTO, HttpSession session);
    MemberDTO getMemberInfo(Long memberId);
}
