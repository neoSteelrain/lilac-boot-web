package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.api.NaruBookExistResposeDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByBookResponseDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByRegionResponseDTO;

/**
 * 나루 도서관 API 연동을 담당한다.
 */
public interface INaruRepository {
    /**
     * 도서의 ISBN 값으로 지역코드 도/특별시/광역시, 시/군/구 에 속하는 도서관목록을 조회한다.
     * @param isbn 도서의 ISBN 13 
     * @param region 도/특별시/광역시 코드
     * @param detailRegion 시/군/구 코드
     * @return 도서관 목록
     */
    NaruLibSearchByBookResponseDTO getLibraryByBook(long isbn, short region, int detailRegion);

    /**
     * 도서의 소장여부, 대출이 가능한지 조회한다.
     * @param isbn 도서의 ISBN 13
     * @param libCode 도서관 코드
     * @return 도서의 소장여부, 대출가능 여부 Y, N
     */
    NaruBookExistResposeDTO checkBookExist(long isbn, int libCode);

    /**
     * 정보공개 도서관 조회
     * @param region 지역코드
     * @param detailRegion 세부지역코드
     * @return 도서관 목록
     */
    NaruLibSearchByRegionResponseDTO getLibraryByRegion(short region, int detailRegion);
}
