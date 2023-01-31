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

import java.util.List;

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
        Long noteId = lectureNoteService.addLectureNote(1L, "1번 강의노트", "첫번째 강의노트 입니다. 열심히 하겠습니다.");
        assertThat(noteId != null).isTrue();
        log.info("noteId = {}", noteId);
    }

    @Test
    @DisplayName("테스트용 데이타 입력")
    public void addLectureNotes(){
        for(int i=1 ; i <= 10 ; i++){
            lectureNoteService.addLectureNote(1L, String.format("%d 번 강의노트", i), String.format("%d 번째 강의노트 설명입니다.열심히 하겠습니다.",i));
        }
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("강의노트 삭제 테스트")
    public void testRemoveLectureNote(){
        Long noteId = lectureNoteService.addLectureNote(1L, "1번 강의노트", "첫번째 강의노트 입니다. 열심히 하겠습니다.");
        assertThat(noteId != null);
        log.info("noteId = {}", noteId);
        lectureNoteService.removeLectureNote(noteId);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("강의노트 수정 테스트")
    public void testModifyLectureNote(){
        Long noteId = lectureNoteService.addLectureNote(1L, "1번 강의노트", "1번 강의노트 설명");
        List<LectureNoteDTO> note1 = lectureNoteService.getLectureListByMember(1L);
        note1.stream().forEach(note -> {
            log.info("수정 전 강의노트 : {}", note);
        });
        log.info("=================== 노트 생성 끝 ================");
        LectureNoteDTO param = LectureNoteDTO.builder()
                .id(noteId)
                .memberId(note1.get(0).getMemberId())
                .title("1번 강의노트 수정 후")
                .description("1번 강의노트 설명 수정 후 ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ")
                .build();
        lectureNoteService.modifyLectureNote(param);
        List<LectureNoteDTO> note2 = lectureNoteService.getLectureListByMember(1L);
        note2.stream().forEach(note -> {
            log.info("수정 후 강의노트 : {}", note);
        });
        log.info("=================== 노트 수정 끝 ================");
    }
}
