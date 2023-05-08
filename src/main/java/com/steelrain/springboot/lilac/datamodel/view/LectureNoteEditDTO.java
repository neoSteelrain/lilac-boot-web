package com.steelrain.springboot.lilac.datamodel.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LectureNoteEditDTO {
    private Long noteId;

    @NotBlank
    @Length(min=1, max=100)
    private String noteTitle;

    @Nullable
    @Length(min=0, max=500)
    private String noteDescription;
    private int licenseId;
    private int subjectId;
}
