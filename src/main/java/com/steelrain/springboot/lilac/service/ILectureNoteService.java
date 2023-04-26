package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.view.BookAddModalDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteDetailDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteStatus;
import com.steelrain.springboot.lilac.datamodel.view.PlayListAddModalDTO;

import java.util.List;

/**
 * 강의노트 서비스 인터페이스
 */
public interface ILectureNoteService {
    void createDefaultLectureNote(Long memberId, String nickname);

    Long addLectureNote(Long memberId, String title, String description, Integer licenseId, Integer subjectId);

    LectureNoteStatus removeLectureNote(Long noteId, Long memberId);

    void editLectureNote(LectureNoteDTO lectureNoteDTO);

    List<LectureNoteDTO> getLectureListByMember(Long memberId);

    boolean addYoutubePlayListToLectureNote(Long lectureNoteId, Long playListId, Long memberId);

    List<PlayListAddModalDTO> getLectureNoteListByPlayListModal(Long memberId, Long playListId);

    LectureNoteDetailDTO getLectureNoteDetailInfoByMember(Long memberId, Long noteId);

    void removePlayList(Long memberId, Long noteId, Long playListId);

    LectureNoteDTO getLectureNoteByMember(Long id, Long noteId);

    void registerBook(Long bookId, Long lectureNoteId, Long memberId);

    List<BookAddModalDTO> getLectureNoteListByBookModal(Long memberId, Long bookId);

    void removeBook(Long refId);
}
