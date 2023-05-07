package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;


@Getter
@Setter
public class LicenseDTO {
    // 자격증코드
    private Integer licenseCode;
    // 자격증 이름
    private String licenseName;

    private List<LicenseStep> licenseStepList;

    private List<LicenseScheduleDTO> scheduleList;
 
    // 자격증의 시험일정을 전부가지고 있는 클래스, API 호출의 결과 JSON을 역직렬화한 한것.
    //private LicenseScheduleResponseDTO.LicenseSchedule licenseSchedule;


    @Getter
    @Setter
    public static class LicenseStep{
        // 진행단계
        private String licStep;
        // 종료일자
        private String licEndDate;
        // 자격증 설명 : 예) XX 기사 (2023년도 제 1회)
        private String licenseDesc;
    }
}
