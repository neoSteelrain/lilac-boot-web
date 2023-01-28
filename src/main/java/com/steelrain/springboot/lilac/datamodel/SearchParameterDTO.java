package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchParameterDTO {

    private String keyword;
    private String subjectKeyword;
    private short regionCode;
    private int detailRegionCode;
}
