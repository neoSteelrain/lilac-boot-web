package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.LectureNoteModalDTO;
import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.PlayListVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteDetailDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteDetailDTO.LectureNoteBook;
import com.steelrain.springboot.lilac.repository.LectureNoteRepository;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LectureNoteMapper {

    int saveDefaultLectureNote(LectureNoteDTO lectureNoteDTO);
    int saveLectureNote(LectureNoteDTO lectureNoteDTO);
    int deleteLectureNote(Long noteId);
    int updateLectureNote(LectureNoteDTO lectureNoteDTO);


    @Select("SELECT id,member_id,license_id,subject_id,title,description,reg_date,progress FROM tbl_lecture WHERE member_id=#{memberId}")
    @Results(id="NoteListByMemberMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "memberId", column = "member_id"),
            @Result(property = "licenseId", column = "license_id"),
            @Result(property = "subjectId", column = "subject_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "regDate", column = "reg_date"),
            @Result(property = "progress", column = "progress")
    })
    List<LectureNoteDTO> findAllNoteListByMember(Long memberId);
    //@Select("SELECT id,member_id,license_id,subject_id,title,description,reg_date,progress FROM tbl_lecture WHERE member_id=#{memberId}")


    @Select("SELECT count(id) FROM tbl_lecture WHERE member_id=#{memberId} AND title=#{title}")
    int findDuplicatedLectureNoteByMember(Long memberId, String title);

    boolean addVideoIdList(List<PlayListVideoDTO> videoIdLis);

    List<LectureNoteModalDTO> findLectureNoteListByPlayList(Long memberId);

    @Select("SELECT id,member_id,license_id,subject_id,title,description,reg_date,progress FROM tbl_lecture WHERE member_id=#{memberId} AND id=#{noteId}")
    LectureNoteDTO findLectureNoteByMember(Long memberId, Long noteId);

    List<LectureNoteDetailDTO.LectureVideoPlayListInfo> findVideoInfoByLectureNote(@Param("memberId")Long memberId, @Param("noteId") Long noteId);

    List<LectureNoteRepository.ChannelTitleInfo> findChannelTitle(List<Long> channelIdList);

    @Select("SELECT duration FROM tbl_youtube WHERE youtube_playlist_id=#{playListId}")
    List<String> findTotalDurationOfPlayList(@Param("playListId") Long playListId);

    void deletePlayList(@Param("memberId") Long memberId, @Param("noteId") Long noteId, @Param("playlistId") Long playListId);

    void addBook(@Param("bookId") Long bookId, @Param("lectureNoteId") Long lectureNoteId, @Param("memberId") Long memberId);

    List<LectureNoteBook> findBookListByLectureNote(Long memberId, Long noteId);

    List<LectureNoteModalDTO> findLectureNoteListByBook(@Param("memberId") Long memberId);

    void deleteBook(@Param("refId") Long refId);
}