package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class LicenseDTO {
    // 자격증코드
    private Integer licenseCode;
    // 자격증 이름
    private String licenseName;

    // 자격증 설명
    private String licenseDesc;

    // 진행단계
    private String licStep;
    // 종료일자
    private String licEndDate;

    private List<LicenseScheduleDTO> scheduleList;
 
    // 자격증의 시험일정을 전부가지고 있는 클래스, API 호출의 결과 JSON을 역직렬화한 한것.
    //private LicenseScheduleResponseDTO.LicenseSchedule licenseSchedule;


    @Override
    public String toString() {
        return "LicenseDTO{" +
                "licenseCode=" + licenseCode +
                ", licenseName='" + licenseName + '\'' +
                ", licenseDesc='" + licenseDesc + '\'' +
                ", licStep='" + licStep + '\'' +
                ", licEndDate='" + licEndDate + '\'' +
                ", scheduleList=" + scheduleList +
                '}';
    }
}
