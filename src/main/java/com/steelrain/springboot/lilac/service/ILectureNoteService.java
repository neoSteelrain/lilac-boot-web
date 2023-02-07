package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;

import java.util.List;

public interface ILectureNoteService {
    void createDefaultLectureNote(Long memberId, String nickname);
    Long addLectureNote(Long memberId, String title, String description, Integer licenseId, Integer subjectId);
    void removeLectureNote(Long noteId);
    void modifyLectureNote(LectureNoteDTO lectureNoteDTO);
    List<LectureNoteDTO> getLectureListByMember(Long memberId);

    boolean addYoutubePlayListToLectureNote(Long lectureNoteId, Long playListId, Long memberId);

    List<LectureNoteDTO> getLectureNoteListByMemberModal(Long memberId, Long playListId);
}
