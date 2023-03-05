package com.steelrain.springboot.lilac.datamodel.view;

import lombok.Getter;
import lombok.Setter;

/**
 * 주제어관련 도서정보를 나타내는 DTO
 */
@Getter
@Setter
public class SubjectBookListDTO extends BookSearchResultDTO{
    private String keyword;
    private int subjectCode;
    private String subjectName;
}
