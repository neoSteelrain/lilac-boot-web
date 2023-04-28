package com.steelrain.springboot.lilac.datamodel;

import com.steelrain.springboot.lilac.common.PageDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class AdminBookSearchResultDTO {
    private PageDTO pageDTO;
    private List<AdminBookDTO> bookList;
}
