package com.steelrain.springboot.lilac.common;

/**
 * 페지징 기능이 필요할때 페이지번호를 계산해주는 클래스
 */
public class PagingUtils {
    private final static int BLOCK_LIMIT = 10;

    public static PageDTO createPagingInfo(int totalCount, int page, int bookCount){
        int maxPage =  (int) Math.ceil((double)totalCount / bookCount);
        int startPage =  (((int)Math.ceil((double)page / BLOCK_LIMIT))-1) * BLOCK_LIMIT + 1;
        int endPage = startPage + BLOCK_LIMIT -1;
        if(endPage > maxPage){
            endPage = maxPage;
        }
        return PageDTO.builder()
                .page(page)
                .startPage(startPage)
                .endPage(endPage)
                .maxPage(maxPage)
                .build();
    }
}
