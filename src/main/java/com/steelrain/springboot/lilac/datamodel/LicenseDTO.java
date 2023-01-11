package com.steelrain.springboot.lilac.datamodel;

import com.steelrain.springboot.lilac.datamodel.api.LicenseScheduleResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LicenseDTO {
    private int licenseCode;
    private String licenseName;
    // 자격증의 시험일정을 전부가지고 있는 클래스, API 호출의 결과 JSON을 역직렬화한 한것.
    private LicenseScheduleResponseDTO.LicenseSchedule licenseSchedule;

    /*
     뷰에 사용할 필드들. DB와는 상관이 없다.
     */
    // 진행단계
    private String licStep;
    // 종료일자
    private String licEndDate;
    // 자격증 설명
    private String licDesc;
}
