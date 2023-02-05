package com.steelrain.springboot.lilac.datamodel.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
