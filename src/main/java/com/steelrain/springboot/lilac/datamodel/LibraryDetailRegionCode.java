package com.steelrain.springboot.lilac.datamodel;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 정보나루 API 검색조건 중 지역구코드정보
 * 예) 종로구, 미추홀구, 용산구
 */
@Data
public class LibraryDetailRegionCode {
    /*
    code	char(5)	NO	PRI
    name	varchar(10)	NO
    detail_name	varchar(10)	NO
    region_code	char(2)	NO	PRI
     */

    @Size(max=5)
    @NotNull
    @NotEmpty
    private String code;

    @Size(max=10)
    @NotNull
    @NotEmpty
    private String name;

    @Size(max=10)
    @NotNull
    @NotEmpty
    private String detailName;
}
