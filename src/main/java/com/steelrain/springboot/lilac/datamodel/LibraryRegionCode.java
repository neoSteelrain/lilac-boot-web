package com.steelrain.springboot.lilac.datamodel;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 정보나루 API 검색조건 중 지역코드
 * 예) 서울, 인천, 경기, 제주
 */
@Data
public class LibraryRegionCode {
    /*
    code	char(2)	NO	PRI
    name	char(2)	NO
     */
    @Size(max=2)
    @NotNull
    @NotEmpty
    private String code;

    @Size(max=2)
    @NotNull
    @NotEmpty
    private String name;
}
