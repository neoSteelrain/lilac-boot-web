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
        if(licenseCode <= 0){
            log.error(String.format("유효하지 않은 자격증코드 : %d", licenseCode));
            // TODO : 잘못된 자격증코드에 대한 에러처리가 필요하다
        }
        model.addAttribute("licenseInfo", m_searchService.getLicenseInfoByCode(licenseCode));
        return "/search/license-template";
    }

    @GetMapping("/bookList")
    public String getBookList(@RequestParam("keyword") String keyword, short region, int detailRegion, Model model){

        model.addAttribute("licenseBookList", m_searchService.getLicenseBookList(keyword, region, detailRegion));
        return "/search/book-template";
    }
}
