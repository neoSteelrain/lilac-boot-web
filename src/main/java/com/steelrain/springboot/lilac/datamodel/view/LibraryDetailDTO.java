package com.steelrain.springboot.lilac.datamodel.view;


import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class LibraryDetailDTO {

    private Long id;
    private String libCode;
    private String name;
    private String address;
    private String tel;
    private String fax;
    private String latitude;
    private String longitude;
    private String homepage;
    private String closed;
    private String operatingTime;
    private Timestamp regDate;
    private String isbn13;

    // 소장가능여부 : DB와는 상관없는 필드
    private boolean hasBook;

    // 대출가능여부 : DB와는 상관없는 필드
    private boolean isLoanAvailable;
}
