package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.PlayListVideoDTO;
import com.steelrain.springboot.lilac.event.VideoListByPlayListEvent;
import com.steelrain.springboot.lilac.exception.LectureNoteException;
import com.steelrain.springboot.lilac.repository.LectureNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureNoteService implements ILectureNoteService{

    private final LectureNoteRepository m_lectureNoteRepository;
    private final ApplicationEventPublisher m_appEventPublisher;


    @Override
    public void createDefaultLectureNote(Long memberId, String nickname){
        try{
            createLectureNote(memberId, String.format("%s 님의 기본강의노트", nickname), null, null);
        }catch(Exception ex){
            log.error("기본강의 노트 생성 예외 - createDefaultLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException( String.format("기본 강의노트 생성 실패 - 회원 ID : %s", memberId), ex, memberId);
        }
    }

    @Override
    public Long addLectureNote(Long memberId, String title, String description, Integer licenseId, Integer subjectId){
        Long noteId = null;
        try{
            if(checkDuplicatedLectureNoteByMember(memberId, title)){
                return null;
            }
            /*
             licenseId 는 자격증 id, subjectId 는 키워드 id 이다.
             licenseId, subjectId 는 XOR 관계 이기 떄문에 하나만 선택해야 한다.
             */
            noteId = createLectureNote(memberId, title, description, licenseId, subjectId);
        }catch(Exception ex){
            log.error("강의 노트 생성 예외 - addLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException( String.format("강의노트 생성 실패 - 회원 ID : %s", memberId), ex, memberId);
        }
        return noteId;
    }

    private boolean checkDuplicatedLectureNoteByMember(Long memberId, String title){
        return m_lectureNoteRepository.checkDuplicatedLectureNoteByMember(memberId, title);
    }

    @Override
    public void removeLectureNote(Long noteId){
        try{
            deleteLectureNote(noteId);
        }catch(Exception ex){
            log.error("강의노트 삭제 예외 - removeLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException( String.format("강의노트 삭제 실패 - 강의노트 ID : %s", noteId), ex, noteId);
        }
    }

    @Override
    public void modifyLectureNote(LectureNoteDTO lectureNoteDTO) {
        try {
            updateLectureNote(lectureNoteDTO);
        } catch (Exception ex) {
            log.error("강의노트 수정 예외 - modifyLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException(String.format("강의노트 삭제 실패 - 강의노트 정보 : %s", lectureNoteDTO.toString()), ex, lectureNoteDTO);
        }
    }

    @Override
    public List<LectureNoteDTO> getLectureListByMember(Long memberId){
        return m_lectureNoteRepository.findNoteListByMember(memberId);
    }

    @Override
    public boolean addYoutubePlayListToLectureNote(Long lectureNoteId, Long playListId, Long memberId) {
        VideoListByPlayListEvent event = VideoListByPlayListEvent.builder()
                                                                 .playListId(playListId)
                                                                 .memberId(memberId)
                                                                 .lectureNoteId(lectureNoteId)
                                                                 .build();
        m_appEventPublisher.publishEvent(event);
        /*
        유튜브영상 id 목록가져오기
        ref_tbl_lecture_youtube 테이블에 insert 하기
        insert 성공여부 반환
         */
        List<Long> videoIdList = event.getVideoDTOList();
        List<PlayListVideoDTO> paramList = new ArrayList<>(videoIdList.size());
        for(Long videoId : videoIdList){
            PlayListVideoDTO dto = new PlayListVideoDTO();
            dto.setLectureId(lectureNoteId);
            dto.setLectureMemberId(memberId);
            dto.setYoutubeId(videoId);
            paramList.add(dto);
        }
        return  m_lectureNoteRepository.addVideoIdList(paramList);
    }

    private List<LectureNoteDTO> findNoteListByMember(Long memberId){
        return m_lectureNoteRepository.findNoteListByMember(memberId);
    }

    private void updateLectureNote(LectureNoteDTO lectureNoteDTO){
        m_lectureNoteRepository.updateLectureNote(lectureNoteDTO);
    }

    private void deleteLectureNote(Long noteId){
        m_lectureNoteRepository.deleteLectureNote(noteId);
    }

    private Long createLectureNote(Long memberId, String title, Integer licenseId, Integer subjectId){
        LectureNoteDTO lectureNoteDTO = LectureNoteDTO.builder()
                .memberId(memberId)
                .title(title)
                .licenseId(licenseId)
                .subjectId(subjectId)
                .build();
        m_lectureNoteRepository.saveDefaultLectureNote(lectureNoteDTO);
        return lectureNoteDTO.getId();
    }

    private Long createLectureNote(Long memberId, String title, String description, Integer licenseId, Integer subjectId){
        LectureNoteDTO lectureNoteDTO = LectureNoteDTO.builder()
                .memberId(memberId)
                .title(title)
                .description(description)
                .licenseId(licenseId)
                .subjectId(subjectId)
                .build();
        m_lectureNoteRepository.saveLectureNote(lectureNoteDTO);
        return lectureNoteDTO.getId();
    }
}
