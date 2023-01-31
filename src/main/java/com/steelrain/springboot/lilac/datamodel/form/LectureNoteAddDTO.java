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
    @Length(min=10, max=100)
    private String title;

    @NotBlank
    @Length(min=10, max=500)
    private String description;
}
