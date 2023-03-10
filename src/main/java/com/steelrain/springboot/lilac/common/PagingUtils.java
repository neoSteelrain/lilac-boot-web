package com.steelrain.springboot.lilac.common;

/**
 * 페지징 기능이 필요할때 페이지번호를 계산해주는 클래스
 */
public class PagingUtils {

    // 페이지 번호의 개수, 예) BLOCK_LIMIT 이 10일 경우 = 이전, 1,2 ... 9,10 다음
    private final static int BLOCK_LIMIT = 10;

    /**
     * 페이지 하단의 페이징 번호를 계산한 다음 PageDTO에 담아서 반환한다
     * @param totalCount 페이징할 전체 페이지 개수
     * @param page 페이징을 시작할 페이지번호
     * @param itemCount 한페이지에 들어갈 아이템의 개수, 예) 게시판의 경우 = 게시물의 개수
     * @return 페이징 정보가 담겨 있는 DTO
     */
    public static PageDTO createPagingInfo(int totalCount, int page, int itemCount){
        int maxPage =  (int) Math.ceil((double)totalCount / itemCount);
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
