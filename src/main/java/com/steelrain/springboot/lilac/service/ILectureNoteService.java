package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteDetailDTO;
import com.steelrain.springboot.lilac.datamodel.view.PlayListAddModalDTO;

import java.util.List;

public interface ILectureNoteService {
    void createDefaultLectureNote(Long memberId, String nickname);
    Long addLectureNote(Long memberId, String title, String description, Integer licenseId, Integer subjectId);
    void removeLectureNote(Long noteId);
    void editLectureNote(LectureNoteDTO lectureNoteDTO);
    List<LectureNoteDTO> getLectureListByMember(Long memberId);
    boolean addYoutubePlayListToLectureNote(Long lectureNoteId, Long playListId, Long memberId);
    List<PlayListAddModalDTO> getLectureNoteListByMemberModal(Long memberId, Long playListId);

    LectureNoteDetailDTO getLectureNoteDetailInfoByMember(Long memberId, Long noteId);

    void removePlayList(Long memberId, Long noteId, Long playListId);

    LectureNoteDTO getLectureNoteByMember(Long id, Long noteId);
}
