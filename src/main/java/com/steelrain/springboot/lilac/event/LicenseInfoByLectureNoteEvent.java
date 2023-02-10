package com.steelrain.springboot.lilac.event;

import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 강의노트 상세정보에서 회원의 자격증정보를 요청하는 이벤트
 */
@Getter
@Builder
public class LicenseInfoByLectureNoteEvent {
    private Integer licenseId;

    @Setter
    private LicenseDTO licenseDTO;
}
