package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.mapper.LectureNoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureNoteRepository {

    private final LectureNoteMapper m_lectureNoteMapper;

    public boolean saveDefaultLectureNote(LectureNoteDTO lectureNoteDTO){
        return m_lectureNoteMapper.saveDefaultLectureNote(lectureNoteDTO) > 0;
    }

    public boolean saveLectureNote(LectureNoteDTO lectureNoteDTO){
        return m_lectureNoteMapper.saveLectureNote(lectureNoteDTO) > 0;
    }

    public boolean deleteLectureNote(Long noteId){
        return m_lectureNoteMapper.deleteLectureNote(noteId) > 0;
    }

    public boolean updateLectureNote(LectureNoteDTO lectureNoteDTO) {
        return m_lectureNoteMapper.updateLectureNote(lectureNoteDTO) > 0;
    }

    public List<LectureNoteDTO> findNoteListByMember(Long memberId) {
        return m_lectureNoteMapper.findNoteListByMember(memberId);
    }

    public boolean checkDuplicatedLectureNoteByMember(Long memberId, String title) {
        return m_lectureNoteMapper.findDuplicatedLectureNoteByMember(memberId, title) > 0;
    }
}
