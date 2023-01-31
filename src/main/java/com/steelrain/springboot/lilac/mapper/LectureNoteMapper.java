package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LectureNoteMapper {

    int saveDefaultLectureNote(LectureNoteDTO lectureNoteDTO);
    int saveLectureNote(LectureNoteDTO lectureNoteDTO);
    int deleteLectureNote(Long noteId);
    int updateLectureNote(LectureNoteDTO lectureNoteDTO);
}
