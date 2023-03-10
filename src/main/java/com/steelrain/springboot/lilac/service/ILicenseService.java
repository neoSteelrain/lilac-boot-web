package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LicenseDTO;

import java.util.List;

/**
 * 자격증 서비스 인터페이스
 */
public interface ILicenseService {
    public LicenseDTO getLicenseSchedulesByCode(int licenseCode);

    public LicenseDTO getLicenseSchedulesById(int licenseId);
}
