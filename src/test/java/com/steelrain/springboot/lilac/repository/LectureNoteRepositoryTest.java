package com.steelrain.springboot.lilac.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class LectureNoteRepositoryTest {
    @Autowired
    private ILectureNoteRepository m_lectureNoteRepository;

    @Test
    void findTotalProgress() {
        long tmp = m_lectureNoteRepository.findTotalProgress(2L, 9L, 23L);
        assertThat(tmp >= 0).isTrue();
    }
}