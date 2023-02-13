package com.steelrain.springboot.lilac.datamodel.view;

import lombok.Builder;
import lombok.Getter;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
public class BookDetailDTO {
    private Long id;
    private String isbn13;
    private String title;
    private String contents;
    private String url;
    private Timestamp publishDate;
    private String authors;
    private String publisher;
    private String translators;
    private Integer price;
    private Integer salePrice;
    private String thumbnail;
    private String status;

    private Timestamp regDate;
    private Timestamp updateDate;

    private List<LibraryDetailDTO> libraryList;
}
