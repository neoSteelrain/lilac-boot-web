package com.steelrain.springboot.lilac.datamodel;

import lombok.*;

import java.util.Objects;

/**
 * 강의노트 추가모달창을 위한 모달창 정보 전용 DTO
 * 회원의 강의노트를 조회하는 DB 쿼리 결과에 매핑시키기 위해서만 쓰는 DTO 이기때문에 playListId가 강의노트1개 마다 1개씩 붙어있다
 */
@Getter
@Setter
@ToString
public class LectureNoteModalDTO {
    private Long noteId;
    private String noteTitle;
    private Long playListId;
    private Long bookId;

    // 스트림 연산 또는 List 같은 자료구조에 사용될때 중복체크를 위해 equals, hashCode를 재정의 해준다
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureNoteModalDTO that = (LectureNoteModalDTO) o;
        return Objects.equals(noteId, that.noteId) && Objects.equals(noteTitle, that.noteTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, noteTitle);
    }
}
