package com.steelrain.springboot.lilac.datamodel.view;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureNoteStatus {
    private boolean isDeleted;
    private boolean isLast;
}
