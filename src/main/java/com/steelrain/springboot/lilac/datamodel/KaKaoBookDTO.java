package com.steelrain.springboot.lilac.datamodel;

import lombok.*;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KaKaoBookDTO {
    /*
    id	bigint	NO	PRI		auto_increment
    isbn13	bigint	NO	UNI
    title	varchar(1024)	YES	MUL
    contents	varchar(2048)	YES
    url	varchar(2048)	YES
    publish_date	datetime	YES
    authors	varchar(255)	YES
    publisher	varchar(255)	YES
    translators	varchar(255)	YES
    price	int	YES		0
    sale_price	int	YES		0
    thumbnail	varchar(255)	YES
    status	varchar(10)	YES
    reg_date	datetime	YES		CURRENT_TIMESTAMP	DEFAULT_GENERATED
    update_date	datetime	YES
     */

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

    private Timestamp publishDate;

    @Size(max=255)
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
    private Timestamp updateDate;

    private Long isbn13Long;
}
