package com.steelrain.springboot.lilac.datamodel.view;

import com.steelrain.springboot.lilac.datamodel.KaKaoBookDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubjectBookListDTO {
    private String keyword;
    private String subjectName;
    private List<KaKaoBookDTO> kakaoBookList;
}
