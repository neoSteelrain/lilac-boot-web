package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 미리 등록된 주제어 검색을 요청하는 이벤트
 */
@Getter
@Builder
public class SubjectBookSearchEvent {
    private int subjectId;
    private int subjectCode;
    private String subjectName;
    private String keyword;
    private int pageNum;
    private int subjectBookCount;

    @Setter
    private SubjectBookListDTO searchResultDTO;
}
