package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.exception.LectureNoteException;
import com.steelrain.springboot.lilac.repository.LectureNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.rmi.server.ExportException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureNoteService implements ILectureNoteService{

    private final LectureNoteRepository m_lectureNoteRepository;


    @Override
    public void createDefaultLectureNote(Long memberId, String nickname){
        try{
            createLectureNote(memberId, String.format("%s 님의 기본강의노트", nickname));
        }catch(Exception ex){
            log.error("기본강의 노트 생성 예외 - createDefaultLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException( String.format("기본 강의노트 생성 실패 - 회원 ID : %s", memberId), ex, memberId);
        }
    }

    @Override
    public Long addLectureNote(Long memberId, String title, String description){
        Long noteId = null;
        try{
            noteId = createLectureNote(memberId, title, description);
        }catch(Exception ex){
            log.error("강의 노트 생성 예외 - addLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException( String.format("강의노트 생성 실패 - 회원 ID : %s", memberId), ex, memberId);
        }
        return noteId;
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

    private List<LectureNoteDTO> findNoteListByMember(Long memberId){
        return m_lectureNoteRepository.findNoteListByMember(memberId);
    }

    private void updateLectureNote(LectureNoteDTO lectureNoteDTO){
        m_lectureNoteRepository.updateLectureNote(lectureNoteDTO);
    }

    private void deleteLectureNote(Long noteId){
        m_lectureNoteRepository.deleteLectureNote(noteId);
    }

    private Long createLectureNote(Long memberId, String title){
        LectureNoteDTO lectureNoteDTO = LectureNoteDTO.builder()
                .memberId(memberId)
                .title(title)
                .build();
        m_lectureNoteRepository.saveDefaultLectureNote(lectureNoteDTO);
        return lectureNoteDTO.getId();
    }

    private Long createLectureNote(Long memberId, String title, String description){
        LectureNoteDTO lectureNoteDTO = LectureNoteDTO.builder()
                .memberId(memberId)
                .title(title)
                .description(description)
                .build();
        m_lectureNoteRepository.saveLectureNote(lectureNoteDTO);
        return lectureNoteDTO.getId();
    }
}
