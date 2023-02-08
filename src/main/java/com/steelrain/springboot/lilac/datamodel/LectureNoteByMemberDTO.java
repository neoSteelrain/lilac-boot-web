package com.steelrain.springboot.lilac.datamodel;

import lombok.*;

import java.util.Objects;

/**
 * 회원의 모든 강의노트와 각각의 강의노트에 들어있는 재생목록을 가진 DTO
 */
@Getter
@Setter
@ToString
public class LectureNoteByMemberDTO {
    private Long noteId;
    private String noteTitle;
    private Long playListId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureNoteByMemberDTO that = (LectureNoteByMemberDTO) o;
        return Objects.equals(noteId, that.noteId) && Objects.equals(noteTitle, that.noteTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, noteTitle);
    }
}
