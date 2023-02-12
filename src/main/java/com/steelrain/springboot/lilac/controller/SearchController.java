package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;
import com.steelrain.springboot.lilac.service.ISearchService;
import com.steelrain.springboot.lilac.service.KeywordCategoryCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.ISBN;
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

    @GetMapping("/bookList")
    public String getBookList(@RequestParam("keyword") String keyword, short region, int detailRegion, Model model){
        if(region <= -1){
            log.error(String.format("지역코드 입력에러 - 필수입력 지역코드가 없음 : 입력된 지역코드 = %d", region));
            return "redirect:/";
        }
        if(region <= -1 && detailRegion < -1){
            log.error(String.format("지역코드 입력에러 - 지역코드,세부지역코드 2개 다 없음 : 입력된 지역코드 = %d , 입력된 세부지역코드 = %d", region, detailRegion));
            return "redirect:/";
        }
        model.addAttribute("licenseBookInfo", m_searchService.getLicenseBookList(keyword, region, detailRegion));
        model.addAttribute("region", region);
        model.addAttribute("detailRegion", detailRegion);
        return "/search/book-template";
    }

    @GetMapping("/subject-book-list")
    public String getKeywordBookList(@RequestParam("subjectCode") int subjectCode, Model model){
        SubjectBookListDTO resultDTO = m_searchService.getSubjectBookList(subjectCode);

        model.addAttribute("subjectBookInfo", resultDTO);
        return "/search/subject-book-template";
    }

    @GetMapping("/book-detail")
    public String bookDetailForm(@RequestParam("isbn") String isbn,
                                 @RequestParam("region") short region,
                                 @RequestParam("detailRegion") int detailRegion){
        // TODO ISBN 형식인지 검증이 필요함. @ISBN 어노테이션을 사용하면 가능하나, 그럴려면 DTO를 따로 만들어야 할거같다.
        if(region <= 0){
            log.error(String.format("지역코드 입력에러 - 필수입력 지역코드가 없음 : 입력된 지역코드 = %d", region));
            return "redirect:/";
        }
        if(region <= 0 && detailRegion < 0){
            log.error(String.format("지역코드 입력에러 - 지역코드,세부지역코드 2개 다 없음 : 입력된 지역코드 = %d , 입력된 세부지역코드 = %d", region, detailRegion));
            return "redirect:/";
        }
        
        return "/search/book-detail";
    }

    @GetMapping("/playlist")
    public String searchPlayList(@RequestParam("keyword") String keyword,
                                 @RequestParam("offset") int offset,
                                 @RequestParam("count") int count,
                                 Model model){
        if(offset <= 0){
            log.error(String.format("페이징 파라미터 입력 에러 : 입력된 offset 값 = %d", offset));
            offset = 1; // 잘못된 offset 은 1번째페이지로 설정해준다.
        }

        model.addAttribute("searchResult", m_searchService.searchPlayList(keyword, offset, count));
        return "/search/playlist-template";
    }
}
