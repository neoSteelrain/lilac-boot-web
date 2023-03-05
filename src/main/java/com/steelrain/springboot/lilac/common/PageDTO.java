package com.steelrain.springboot.lilac.common;

import lombok.Builder;
import lombok.Getter;

/**
 * 페이징처리에서 필요한 페이징 정보를 나타내는 클래스
 */
@Getter
@Builder
public class PageDTO {
    private int page; // 현재 페이지
    private int maxPage; // 필요한 페이지 갯수
    private int startPage; // 시작 페이지
    private int endPage; // 끝 페이지
}
