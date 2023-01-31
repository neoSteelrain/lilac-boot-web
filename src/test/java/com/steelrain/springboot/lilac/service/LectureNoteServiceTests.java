package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.exception.LectureNoteException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class LectureNoteServiceTests {

    @Autowired
    private LectureNoteService lectureNoteService;

    @Test
    @Transactional
    @Rollback
    @DisplayName("기본 강의노트 생성 테스트")
    public void testCreateDefaultLectureNote(){

        lectureNoteService.createDefaultLectureNote(4L, "user3");

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("기본 강의노트 생성 예외 발생 테스트")
    public void testCreateDefaultLectureNoteException(){
        Throwable exception = assertThrows(LectureNoteException.class, () -> {
            lectureNoteService.createDefaultLectureNote(100L, "user100");
        });
        assertEquals("기본 강의노트 생성 실패 - 회원 ID : 100", exception.getMessage());
    }
    
    @Test
    @Transactional
    @Rollback
    @DisplayName("강의노트 추가 테스트")
    public void testAddLectureNote(){
        Long noteId = lectureNoteService.addLectureNote(4L, "1번 강의노트", "첫번째 강의노트 입니다. 열심히 하겠습니다.");
        assertThat(noteId != null).isTrue();
        log.info("noteId = {}", noteId);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("강의노트 삭제 테스트")
    public void testRemoveLectureNote(){
        Long noteId = lectureNoteService.addLectureNote(4L, "1번 강의노트", "첫번째 강의노트 입니다. 열심히 하겠습니다.");

        lectureNoteService.removeLectureNote(noteId);
    }
}
