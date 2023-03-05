package com.steelrain.springboot.lilac.datamodel.view;

import lombok.Getter;
import lombok.Setter;


/**
 * 자격증관련 도서정보를 나타내는 DTO
 */
@Getter
@Setter
public class LicenseBookListDTO extends BookSearchResultDTO {
    private int licenseCode;
    private String keyword;
}
