package com.steelrain.springboot.lilac.service;


import com.steelrain.springboot.lilac.datamodel.LicenseBookDTO;

import java.util.List;
import java.util.Map;

public interface IBookService {
    List<LicenseBookDTO> getLicenseBookList(String keyword, short region, int detailRegion);

}
