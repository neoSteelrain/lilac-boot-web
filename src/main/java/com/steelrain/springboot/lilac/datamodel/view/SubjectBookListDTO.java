package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import com.steelrain.springboot.lilac.datamodel.PageDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectBookListDTO {
    private String keyword;
    private String subjectName;
    private PageDTO pageDTO;
    private List<KaKaoBookDTO> kakaoBookList;
}
