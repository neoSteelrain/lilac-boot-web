package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;

import java.util.List;

public interface ILectureNoteService {
    void createDefaultLectureNote(Long memberId, String nickname);
    Long addLectureNote(Long memberId, String title, String description);
    void removeLectureNote(Long noteId);
    void modifyLectureNote(LectureNoteDTO lectureNoteDTO);
    List<LectureNoteDTO> getLectureListByMember(Long memberId);
}
