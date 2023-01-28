package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.event.LicenseSearchEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchEventHandler {

    private final ILicenseService m_licenseService;

    @EventListener
    public void handleLicenseSearchEvent(LicenseSearchEvent event){
        event.setLicenseDTO(m_licenseService.getLicenseSchedulesByCode(event.getCode()));
    }
}
