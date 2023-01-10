package com.steelrain.springboot.lilac.domain;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import com.steelrain.springboot.lilac.datamodel.api.KakaoSearchedBookDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByBookResponseDTO;

import java.util.List;
import java.util.Map;

/**
 * 도서검색 도메인 업무 처리 인터페이스
 */
public interface IBookManager {

    Map<KaKaoBookDTO, List<NaruLibraryDTO>> createSearchedBookLibraryList(List<KakaoSearchedBookDTO> bookList,
                                                                          List<NaruLibSearchByBookResponseDTO.Library> libraryList);
}
