package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.common.PageDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectBookListDTO {
    private String keyword;
    private int subjectCode;
    private int regionCode;
    private int detailRegionCode;
    private String subjectName;
    private String regionName;
    private String detailRegionName;

    private int totalBookCount;
    private PageDTO pageInfo;
    private List<KaKaoBookDTO> kakaoBookList;
}
