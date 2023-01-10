package com.steelrain.springboot.lilac.domain;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.NaruLibraryDTO;
import com.steelrain.springboot.lilac.datamodel.api.KakaoSearchedBookDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibSearchByBookResponseDTO;

import java.util.List;
import java.util.Map;

public class BookManager implements IBookManager{

    @Override
    public Map<KaKaoBookDTO, List<NaruLibraryDTO>> createSearchedBookLibraryList(List<KakaoSearchedBookDTO> bookList, List<NaruLibSearchByBookResponseDTO.Library> libraryList) {

        return null;
    }
}
