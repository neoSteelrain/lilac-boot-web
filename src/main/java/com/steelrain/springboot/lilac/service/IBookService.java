package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;

import java.util.List;
import java.util.Map;

public interface IBookService {
    public Map<KaKaoBookDTO, List<NaruLibraryDTO>> getKakaoBookLibraryList(String keyword, String region, String detailRegion);

}
