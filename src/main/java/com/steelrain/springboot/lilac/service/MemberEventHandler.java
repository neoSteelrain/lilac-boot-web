package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.event.MemberRegistrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberEventHandler {

    private final LectureNoteService m_lectureNoteService;


    @Async
    @EventListener(MemberRegistrationEvent.class)
    public void createDefaultLectureNoteByNewMember(MemberRegistrationEvent event){
        m_lectureNoteService.createDefaultLectureNote(event.getMemberId(), event.getMemberNickname());
    }
}
