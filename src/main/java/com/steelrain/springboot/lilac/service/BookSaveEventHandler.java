package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.event.KakaoBookSaveEvent;
import com.steelrain.springboot.lilac.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSaveEventHandler {

    private final BookRepository m_bookRepository;


    /*
        카카오책 검색결과 저장이벤트는 저장기능만 있어서 별도의 Service를 만들지 않고 Repository 에서 바로 처리한다.
     */
    @Async
    @EventListener(KakaoBookSaveEvent.class)
    public void handleBookSaveEvent(KakaoBookSaveEvent event){
        m_bookRepository.saveKakaoBookList(event.getKaKaoBookList());
    }
}
