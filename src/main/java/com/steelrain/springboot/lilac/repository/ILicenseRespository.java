package com.steelrain.springboot.lilac.repository;


import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import com.steelrain.springboot.lilac.datamodel.api.LicenseScheduleResponseDTO;

import java.util.List;

/**
 * 자격증 시험일정 조회 인터페이스
 */
public interface ILicenseRespository {
    public List<LicenseScheduleResponseDTO.LicenseSchedule> getLicenseSchedule(int licenseCode);

    public LicenseDTO getLicenseInfo(int licenseCode);
}
