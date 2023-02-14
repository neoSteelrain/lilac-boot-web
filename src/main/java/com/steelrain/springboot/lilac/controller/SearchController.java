package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.common.YOUTUBE_PAGING_INFO;
import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;
import com.steelrain.springboot.lilac.service.ISearchService;
import com.steelrain.springboot.lilac.common.KeywordCategoryCacheService;
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
            log.error(String.format("유효하지 않은 지역코드 : 입력된 지역코드 = %d ", regionCode));
            return new ArrayList<>(0);
        }
        return m_keywordCategoryCacheService.getLibraryDetailRegionCodeList(regionCode);
    }

    @GetMapping("/licSchd")
    public String getLicenseInfo(@RequestParam("licenseCode") int licenseCode, Model model){
        if(licenseCode <= 0){
            log.error(String.format("유효하지 않은 자격증코드 : 입력된 자격증코드 = %d", licenseCode));
            return "redirect:/";
        }
        model.addAttribute("licenseInfo", m_searchService.getLicenseInfoByCode(licenseCode));
        return "/search/license-template";
    }

    @GetMapping("/licenseBookList")
    public String getLicenseBookList(@RequestParam("licenseCode") int licenseCode, short regionCode, int detailRegionCode, int pageNum, int bookCount, Model model){
        if(regionCode <= -1){
            log.error(String.format("지역코드 입력에러 - 필수입력 지역코드가 없음 : 입력된 지역코드 = %d", regionCode));
            return "redirect:/";
        }
        if(regionCode <= -1 && detailRegionCode < -1){
            log.error(String.format("지역코드 입력에러 - 지역코드,세부지역코드 2개 다 없음 : 입력된 지역코드 = %d , 입력된 세부지역코드 = %d", regionCode, detailRegionCode));
            return "redirect:/";
        }
        model.addAttribute("licenseBookInfo", m_searchService.getLicenseBookList(licenseCode, regionCode, detailRegionCode,
                                                                                             pageNum, bookCount));
        model.addAttribute("region", regionCode);
        model.addAttribute("detailRegion", detailRegionCode);
        return "/search/book-template";
    }

    @GetMapping("/subject-book-list")
    public String getSubjectBookList(@RequestParam("subjectCode") int subjectCode,
                                     @RequestParam("pageNum") int pageNum,
                                     @RequestParam("bookCount") int bookCount, Model model){

        SubjectBookListDTO resultDTO = m_searchService.getSubjectBookList(subjectCode, pageNum,  bookCount);

        model.addAttribute("subjectBookInfo", resultDTO);
        return "/search/subject-book-template";
    }

    @GetMapping("/book-detail")
    public String bookDetailInfo(@RequestParam("isbn") Long isbn,
                                 @RequestParam(value = "region", required = false) short region,
                                 @RequestParam(value = "detailRegion",  required = false) int detailRegion,
                                 Model model){
        if(region < 0){
            log.error(String.format("지역코드 입력에러 - 필수입력 지역코드가 없음 : 입력된 지역코드 = %d", region));
            return "redirect:/";
        }
        if(region < 0 && detailRegion < 0){
            log.error(String.format("지역코드 입력에러 - 지역코드,세부지역코드 2개 다 없음 : 입력된 지역코드 = %d , 입력된 세부지역코드 = %d", region, detailRegion));
            return "redirect:/";
        }
        model.addAttribute("bookDetailInfo", m_searchService.getBookDetailInfo(isbn, region, detailRegion));
        return "/search/book-detail";
    }

    @GetMapping("/playlist")
    public String searchPlayList(@RequestParam("keywordCode") int keywordCode,
                                 @RequestParam("pageNum") int pageNum,
                                 @RequestParam("keywordType") int keywordType,
                                 Model model){
        if(pageNum <= 0){
            log.error(String.format("페이징 파라미터 입력 에러 : 입력된 pageNum 값 = %d", pageNum));
            return "redirect:/";
        }


        model.addAttribute("searchResult", m_searchService.searchPlayList(keywordCode, pageNum, YOUTUBE_PAGING_INFO.YOUTUBE_COUNT_PER_PAGE, keywordType));
        return "/search/playlist-template";
    }
}
