package com.steelrain.springboot.lilac.datamodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LicenseScheduleDTO {
    // 구분
    private String category;

    // 필기원서접수기간
    private String docRegPeriod;

    // 필기시험기간
    private String docExam;

    // 필기합격 발표일
    private String docPass;

    // 실기원수접수기간
    private String pracReg;

    // 실기시험기간
    private String pracExam;

    // 최종합격 발표일
    private String pracPass;
}
