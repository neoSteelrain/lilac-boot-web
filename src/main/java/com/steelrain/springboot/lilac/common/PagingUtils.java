package com.steelrain.springboot.lilac.common;

import com.steelrain.springboot.lilac.datamodel.PageDTO;

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
