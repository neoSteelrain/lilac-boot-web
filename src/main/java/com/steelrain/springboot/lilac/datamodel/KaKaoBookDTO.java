package com.steelrain.springboot.lilac.datamodel;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * kakao 책검색 API로 가져온 도서정보 DTO
 *
 */
@Getter
//@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KaKaoBookDTO {
    @NotNull
    private Long id;

    @ISBN
    @Size(max=13)
    @NotEmpty
    @NotNull
    private String isbn13;

    @Size(max=1024)
    private String title;

    @Size(max=2048)
    private String contents;

    @URL
    @Size(max=2048)
    private String url;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Timestamp publishDate;

    @Size(max=255)
    private String authors;

    @Size(max=255)
    private String publisher;

    @Size(max=255)
    private String translators;

    @NumberFormat(pattern="###,###")
    private Integer price;
    @NumberFormat(pattern="###,###")
    private Integer salePrice;

    @Size(max=255)
    private String thumbnail;

    @Size(max=10)
    private String status;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp regDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp updateDate;

    private Long isbn13Long;

    private Integer licenseId;
    private Integer subjectId;

}
