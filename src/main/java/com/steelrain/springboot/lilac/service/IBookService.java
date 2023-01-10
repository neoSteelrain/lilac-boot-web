package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.api.KakaoSearchedBookDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByBookResponseDTO;

import java.util.List;
import java.util.Map;

public interface IBookService {
    public Map<KakaoSearchedBookDTO, List<NaruLibSearchByBookResponseDTO.Library>> getKakaoBookLibraryList(String keyword, short region, int detailRegion);

}
