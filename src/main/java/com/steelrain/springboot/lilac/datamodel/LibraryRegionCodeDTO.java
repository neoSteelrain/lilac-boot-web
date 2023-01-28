package com.steelrain.springboot.lilac.datamodel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 정보나루 API 검색조건 중 지역코드
 * 예) 서울, 인천, 경기, 제주
 */
@Getter
@Setter
@ToString
public class LibraryRegionCodeDTO {
    /*
    code	smallint	NO	PRI
    name	char(2)	NO
     */
    @NotNull
    @NotEmpty
    private Short code;

    @Size(max=2)
    @NotNull
    @NotEmpty
    private String name;
}
