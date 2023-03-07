package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.service.ILicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/license")
public class LicenseController {

    private final ILicenseService m_licenseService;
}
