package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KakaoBookSaveEvent {
    private List<KaKaoBookDTO> kaKaoBookList;
}
