package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class LicenseServiceTests {

    @Autowired
    private ILicenseService m_licILicenseService;

    @Test
    public void testGetLicenseSchedulesByCode() {
        LicenseDTO res = m_licILicenseService.getLicenseSchedulesByCode(2290);

        assertThat(res != null && StringUtils.hasText(res.getLicenseName()));

        System.out.println("자격증 이름 : " + res.getLicenseName());
    }

    @Test
    public void testSetCurrentStep(){
        LicenseDTO res = m_licILicenseService.getLicenseSchedulesByCode(2290);

        assertThat(res).isNotNull();

        System.out.println(res.toString());
    }

    @Test
    public void testParseLicenseScheduleJsonString(){
        LicenseDTO res = m_licILicenseService.getLicenseSchedulesByCode(2290);

        assertThat(res != null && StringUtils.hasText(res.getScheduleList().get(0).getCategory()));

        System.out.println("자격증 구분 : " + res.getScheduleList().get(0).getCategory());
        System.out.println("res.toString() : " + res.toString());
    }

    @Test
    public void testGetLicenseCategoryName(){
        String res = getLicenseCategoryName("국가기술자격 기사 (2023년도 제1회)");

        assertThat("기사".equals(res));

        System.out.println("기사인가? : "+res);
    }

    private String getLicenseCategoryName(String str){
        String[] tmp = StringUtils.split(str, " ");
        return tmp != null && tmp.length > 1 ? tmp[1] : " ";
    }
}
