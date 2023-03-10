package com.steelrain.springboot.lilac.datamodel.view;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BookAddModalDTO {
    private Long id;
    private String title;
    private List<Long> bookIdList;
}
