package com.steelrain.springboot.lilac.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steelrain.springboot.lilac.service.ILectureNoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LectureRestController.class)
@Transactional
class LectureRestControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ILectureNoteService lectureNoteService;


    @Test
    void addLectureNote() throws Exception {
        /*LectureRestController.LectureAddRequest request = LectureRestController.LectureAddRequest.builder()
                .memberId(2L)
                .title("테스트 제목")
                .description("테스트 설명")
                .build();
        ObjectMapper om = new ObjectMapper();
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/lecture/api/addNote")
                .content(om.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isCreated());*/
    }
}