package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageDTO {
    private int page; // 현재 페이지
    private int maxPage; // 필요한 페이지 갯수
    private int startPage; // 시작 페이지
    private int endPage; // 끝 페이지
}
