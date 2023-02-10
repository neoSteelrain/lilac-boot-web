package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import com.steelrain.springboot.lilac.event.LicenseInfoByLectureNoteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureNoteEventHandler {

    private final ILicenseService m_licenseService;


    @EventListener(LicenseInfoByLectureNoteEvent.class)
    public void handleLicenseInfoByLectureNoteEvent(LicenseInfoByLectureNoteEvent event){
        LicenseDTO dto = m_licenseService.getLicenseSchedulesById(event.getLicenseId());
        event.setLicenseDTO(dto);
    }
}
