package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.PlayListVideoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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
    List<LectureNoteDTO> findNoteListByMember(Long memberId);

    @Select("SELECT count(id) FROM tbl_lecture WHERE member_id=#{memberId} AND title=#{title}")
    int findDuplicatedLectureNoteByMember(Long memberId, String title);

    boolean addVideoIdList(List<PlayListVideoDTO> videoIdLis);

    @Select("SELECT id,title FROM tbl_lecture WHERE member_id=#{memberId}")
    @Results(id ="findLectureNoteListByMemberMap", value={
            @Result(property = "id", column="id"),
            @Result(property = "title", column="title")
    })
    List<LectureNoteDTO> findLectureNoteListByMember(Long memberId, Long playListId);
}