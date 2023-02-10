package com.steelrain.springboot.lilac.datamodel.view;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LectureNoteAddDTO {

    private Long memberId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;
}
