package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.datamodel.LibraryRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseCodeDTO;
import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;
import com.steelrain.springboot.lilac.service.ISearchService;
import com.steelrain.springboot.lilac.service.KeywordCategoryCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final KeywordCategoryCacheService m_keywordCategoryCacheService;


    @GetMapping("/")
    public String index(){
        return "index";
    }


    @ModelAttribute("subjectCodes")
    public List<SubjectCodeDTO> getSubjectCodes(){
        return m_keywordCategoryCacheService.getSubjectCodeList();
    }

    @ModelAttribute("libRegionCodes")
    public List<LibraryRegionCodeDTO> getLibRegionCodes(){
        return m_keywordCategoryCacheService.getLibraryRegionCodeList();
    }

    @ModelAttribute("licenseCodes")
    public List<LicenseCodeDTO> getLicenseCodes(){
        return m_keywordCategoryCacheService.getLicenseCodeList();
    }
}
