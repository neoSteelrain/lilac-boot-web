package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.api.NaruBookExistResposeDTO;
import com.steelrain.springboot.lilac.datamodel.api.NaruLibrarySearchByBookResponseDTO;

public interface INaruRepository {
    NaruLibrarySearchByBookResponseDTO getLibraryByBook(long isbn, short region, int detailRegion);

    NaruBookExistResposeDTO checkBookExist(long isbn, short libCode);
}
