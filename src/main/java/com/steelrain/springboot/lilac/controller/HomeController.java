package com.steelrain.springboot.lilac.controller;


import com.steelrain.springboot.lilac.service.KeywordCategoryCacheService;
import com.steelrain.springboot.lilac.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final KeywordCategoryCacheService m_keywordCategoryCacheService;
    private final VideoService m_videoService;


    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("subjectCodes",m_keywordCategoryCacheService.getSubjectCodeList());
        model.addAttribute("libRegionCodes",m_keywordCategoryCacheService.getLibraryRegionCodeList());
        model.addAttribute("licenseCodes",m_keywordCategoryCacheService.getLicenseCodeList());
        model.addAttribute("recommendedVideoList", m_videoService.getRecommendedVideoList());

        return "index";
    }

  /*  @ModelAttribute("subjectCodes")
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
    }*/
}
