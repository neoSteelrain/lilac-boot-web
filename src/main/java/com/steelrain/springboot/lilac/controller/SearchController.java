package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.service.ISearchService;
import com.steelrain.springboot.lilac.service.KeywordCategoryCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final ISearchService m_searchService;
    private final KeywordCategoryCacheService m_keywordCategoryCacheService;


    @GetMapping("/dtlRegionCode")
    @ResponseBody
    public List<LibraryDetailRegionCodeDTO> getLibDetailRegionCodes(@RequestParam("regionCode") short regionCode){
        if(regionCode < 11){ // 지역코드는 11부터 시작한다
            log.error(String.format("유효하지 않은 지역코드 : %d ", regionCode));
            return new ArrayList<>(0);
        }
        return m_keywordCategoryCacheService.getLibraryDetailRegionCodeList(regionCode);
    }

    @GetMapping("/licSchd")
    public String getLicenseInfo(@RequestParam("licenseCode") int licenseCode, Model model){
        model.addAttribute("licenseInfo", m_searchService.getLicenseInfoByCode(licenseCode));
        return "/license/license-template";
    }
}
