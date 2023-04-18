package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.LectureNoteModalDTO;
import com.steelrain.springboot.lilac.datamodel.PlayListVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteDetailDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteDetailDTO.LectureNoteBook;

import java.time.Duration;
import java.util.List;

public interface ILectureNoteRepository {
    boolean saveDefaultLectureNote(LectureNoteDTO lectureNoteDTO);

    boolean saveLectureNote(LectureNoteDTO lectureNoteDTO);

    boolean deleteLectureNote(Long noteId);

    boolean updateLectureNote(LectureNoteDTO lectureNoteDTO);

    List<LectureNoteDTO> findNoteListByMember(Long memberId);

    boolean checkDuplicatedLectureNoteByMember(Long memberId, String title);

    boolean addVideoIdList(List<PlayListVideoDTO> videoIdList);

    List<LectureNoteModalDTO> findLectureNoteListByPlayList(Long memberId);

    LectureNoteDTO findLectureNoteByMember(Long memberId, Long noteId);

    List<LectureNoteDetailDTO.LecturePlayListInfo> findPlayListInfoByLectureNote(Long memberId, Long noteId);

    Duration findTotalDurationOfPlayList(Long playListId);

    void deletePlayList(Long memberId, Long noteId, Long playListId);

    void addBook(Long bookId, Long lectureNoteId, Long memberId);

    List<LectureNoteBook> findBookListByLectureNote(Long memberId, Long noteId);

    List<LectureNoteModalDTO> findLectureNoteListByBook(Long memberId);

    void deleteBook(Long refId);

    String[] findAllDuration(Long playlistId);

    long findTotalProgress(Long memberId, Long noteId, Long playlistId);
}
