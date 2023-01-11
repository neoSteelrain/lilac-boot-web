package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LicenseDTO;

import java.util.List;

public interface ILicenseService {
    public List<LicenseDTO> getLicenseSchedulesByCode(int licenseCode);
}
