package com.steelrain.springboot.lilac.repository;


import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import com.steelrain.springboot.lilac.datamodel.api.LicenseScheduleResponseDTO;

import java.util.List;
import java.util.Optional;

/**
 * 자격증 시험일정 조회 인터페이스
 */
public interface ILicenseRespository {
    public LicenseScheduleResponseDTO getLicenseSchedule(int licenseCode);

    // public Optional<LicenseDTO> getLicenseInfo(int licenseCode);

    public Optional<String> getLicenseName(int licenseCode);
}
