package com.steelrain.springboot.lilac.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/lecture")
public class LectureController {

    @GetMapping("/lecture-note")
    public String lectureNoteForm(){
        return "/lecture/lecture-note";
    }
}
