package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.datamodel.view.MemberProfileEditDTO;
import com.steelrain.springboot.lilac.event.MemberRegistrationEvent;
import com.steelrain.springboot.lilac.exception.DuplicateLilacMemberException;
import com.steelrain.springboot.lilac.exception.LilacRepositoryException;
import com.steelrain.springboot.lilac.repository.IAwsS3Repository;
import com.steelrain.springboot.lilac.repository.IMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 회원 서비스
 * - 회원의 비즈니스 로직을 구현
 * - 회원 관련된 이벤트를 처리/발행한다
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {

    private final IMemberRepository m_memberRepository;
    private final IAwsS3Repository m_awsAwsS3Repository;
    private final ApplicationEventPublisher m_appEventPublisher;


    @Override
    public MemberDTO getMemberInfo(Long memberId) {
        return m_memberRepository.findMemberInfo(memberId);
    }

    @Override
    public boolean checkDuplicatedEmail(String email) {
        return m_memberRepository.findMemberByEmail(email) > 0;
    }

    @Override
    public boolean checkDuplicatedNickName(String nickName) {
        return m_memberRepository.findMemberByNickName(nickName) > 0 ;
    }

    @Transactional
    @Override
    public boolean registerMember(MemberDTO memberDTO) throws DuplicateLilacMemberException, LilacRepositoryException {
        Long id = m_memberRepository.saveMember(memberDTO);
        MemberRegistrationEvent registrationEvent = MemberRegistrationEvent.builder()
                .memberNickname(memberDTO.getNickname())
                .memberId(memberDTO.getId())
                .build();
        m_appEventPublisher.publishEvent(registrationEvent);
        return Objects.nonNull(id);
    }

    @Override
    public MemberDTO loginMember(String email, String password) {
        return m_memberRepository.findMemberByLoginInfo(email, password);
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        return m_memberRepository.findAllMembers();
    }

    @Transactional
    @Override
    public void updateMemberInfo(MemberDTO memberDTO, MemberProfileEditDTO editDTO, HttpSession session) {
        memberDTO.setNickname(editDTO.getNickname());
        memberDTO.setEmail(editDTO.getEmail());
        memberDTO.setDescription(editDTO.getDescription());
        memberDTO.setProfileOriginal(editDTO.getProfileImage().getOriginalFilename());
        memberDTO.setProfileSave(updateMemberProfile(editDTO.getProfileImage(), memberDTO.getId(), memberDTO.getProfileSave()));

        // 세션정보도 같이 업데이트 해준다
        session.setAttribute(SESSION_KEY.MEMBER_NICKNAME, editDTO.getNickname());
        session.setAttribute(SESSION_KEY.MEMBER_EMAIL, editDTO.getEmail());

        m_memberRepository.updateMemberInfo(memberDTO);
    }

    /*
        원본파일의 이름만 변경되고 S3에 있는 키값은 변하지 않아도 되므로
        원본파일 이름만 변경하고 S3에 저장된 이름은 변경되지 않고 덮어쓰기한다.
     */
    private String updateMemberProfile(MultipartFile multipartFile, Long memberId, String originalSavedFileName){
        String originalProfileName = multipartFile.getOriginalFilename();
        // 회원의 프로필이미지가 있으면 이전에 저장된 S3 키값을 사용하고, 프로필이미지가 없으면 새로 키를 만들어준다
        String saveProfileName = StringUtils.isEmpty(originalSavedFileName) ?
                                    convertUUIDFormattedFileName(originalProfileName) :
                                    FilenameUtils.getName(originalSavedFileName);
        String uploadedUrl = m_awsAwsS3Repository.upLoadMemberProfile(multipartFile, saveProfileName);
        m_memberRepository.updateMemberProfile(memberId, originalProfileName, uploadedUrl);
        return uploadedUrl;
    }

    private String convertUUIDFormattedFileName(String origin){
        return UUID.randomUUID().toString() + "." +  FilenameUtils.getExtension(origin);
    }
}