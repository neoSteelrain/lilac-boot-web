package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.event.LicenseBookSearchEvent;
import com.steelrain.springboot.lilac.event.LicenseSearchEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchEventHandler {

    private final ILicenseService m_licenseService;
    private final IBookService m_bookService;


    @EventListener(LicenseSearchEvent.class)
    public void handleLicenseSearchEvent(LicenseSearchEvent event){
        event.setLicenseDTO(m_licenseService.getLicenseSchedulesByCode(event.getCode()));
    }

    @EventListener(LicenseBookSearchEvent.class)
    public void handleLicenseBookSearchEvent(LicenseBookSearchEvent event){
        event.setLicenseBookListDTO(m_bookService.getLicenseBookList(event.getKeyword(), event.getRegion(), event.getDetailRegion()));
    }
}
