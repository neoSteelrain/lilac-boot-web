package com.steelrain.springboot.lilac.datamodel.view;

import lombok.*;

import java.util.List;

/**
 * 회원의 강의노트와 강의노트에 들어있는 재생목록을 나타내는 form 전용 DTO
 * View 에 반환할 목적으로만 사용한다.
 */
@Getter
@Builder
public class PlayListAddModalDTO {
    private Long id;
    private String title;
    private List<Long> playListId;
}
