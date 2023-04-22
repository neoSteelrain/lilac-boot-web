package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.common.PageDTO;
import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 도서검색결과에 포함되는 기본정보를 나타내는 DTO
 */
@Getter
@Setter
public class BookSearchResultDTO {
    // 지역코드
    private int regionCode;
    // 세부지역코드
    private int detailRegionCode;
    // 지역이름
    private String regionName;
    // 세부지역이름
    private String detailRegionName;

    // 검색된 모든 도서의 권수
    private int totalBookCount;
    // 카카오API로 검색된 도서의 목록
    private List<KaKaoBookDTO> kakaoBookList;
    // 도서와 연관된 도서관목록, 지역코드, 세부지역코드로 필터링된 도서관들.
    private List<NaruLibraryDTO> libraryList;

    // 도서관별 도서소장목록을 연관시켜놓은 Map
    private Map<String, List<KaKaoBookDTO>> catalogueMap;
    // 페이징 정보
    private PageDTO pageInfo;
}
