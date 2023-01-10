package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * kakao 책검색 API로 가져온 도서정보 DTO
 *
 */
@Getter
@Setter
public class KaKaoBookDTO {
    /*
    isbn13	char(13)	NO	PRI
    title	varchar(100)	YES
    contents	varchar(500)	YES
    url	varchar(255)	YES
    publish_date	datetime	YES
    authors	varchar(255)	YES
    publisher	varchar(255)	YES
    translators	varchar(255)	YES
    price	int	YES
    sale_price	int	YES
    thumbnail	varchar(255)	YES
    status	varchar(10)	YES
    reg_date	datetime	YES		CURRENT_TIMESTAMP	DEFAULT_GENERATED
     */

    @ISBN
    @Size(max=13)
    @NotEmpty
    @NotNull
    private String isbn13;

    @Size(max=100)
    private String title;

    @Size(max=500)
    private String contents;

    @URL
    @Size(max=255)
    private String url;

    private Timestamp publishDate;

    @Size(max=50)
    private String authors;

    @Size(max=255)
    private String publisher;

    @Size(max=255)
    private String translators;

    private Integer price;
    private Integer salePrice;

    @Size(max=255)
    private String thumbnail;

    @Size(max=10)
    private String status;

    private Timestamp regDate;
}
