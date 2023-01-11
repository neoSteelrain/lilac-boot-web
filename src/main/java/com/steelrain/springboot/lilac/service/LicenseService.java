package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseService implements ILicenseService{
    @Override
    public List<LicenseDTO> getLicenseSchedulesByCode(int licenseCode) {
        return null;
    }
}
