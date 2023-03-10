package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 카카오 도서를 DB에 저장하는 이벤트
 */
@Getter
@Builder
public class KakaoBookSaveEvent {
    private List<KaKaoBookDTO> kaKaoBookList;
}
