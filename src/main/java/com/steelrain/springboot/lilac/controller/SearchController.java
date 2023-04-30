package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.common.ICacheService;
import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.common.YOUTUBE_PAGING_INFO;
import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.datamodel.view.SubjectBookListDTO;
import com.steelrain.springboot.lilac.service.IMemberService;
import com.steelrain.springboot.lilac.service.ISearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final ISearchService m_searchService;
    private final IMemberService m_memberService;
    private final ICacheService m_keywordCategoryCacheService;


    @GetMapping("/dtl-region-code")
    @ResponseBody
    public List<LibraryDetailRegionCodeDTO> getLibDetailRegionCodes(@RequestParam("regionCode") short regionCode){
        if(regionCode < 11){ // 지역코드는 11부터 시작한다
            log.error("유효하지 않은 지역코드 : 입력된 지역코드 = {}", regionCode);
            return new ArrayList<>(0);
        }
        return m_keywordCategoryCacheService.getLibraryDetailRegionCodeList(regionCode);
    }

    @GetMapping("/license-schedule")
    public String getLicenseInfo(@RequestParam("licenseCode") int licenseCode, Model model){
        /*if(licenseCode <= 0){
            log.error("유효하지 않은 자격증코드 : 입력된 자격증코드 = {}", licenseCode);
            return "redirect:/";
        }*/
        model.addAttribute("licenseInfo", m_searchService.getLicenseInfoByCode(licenseCode));
        return "search/license-template";
    }

    @GetMapping("/license-book-list")
    public String getLicenseBookList(@RequestParam("licenseCode") int licenseCode,
                                     @RequestParam("regionCode") short regionCode,
                                     @RequestParam("detailRegionCode") int detailRegionCode,
                                     @RequestParam("pageNum") int pageNum,
                                     @RequestParam("bookCount") int bookCount, Model model, HttpSession session){
        MemberDTO memberDTO = m_memberService.getMemberInfo((Long)session.getAttribute(SESSION_KEY.MEMBER_ID));
        boolean isLogin = Objects.nonNull(memberDTO);
        if(regionCode <= -1){
            if(isLogin){
                regionCode = memberDTO.getRegion();
            }else{
                log.error("지역코드 입력에러 - 필수입력 지역코드 없음 : 입력된 지역코드 = {}", regionCode);
                return "redirect:/";
            }
        }
        if(regionCode <= -1 && detailRegionCode < -1){
            if(isLogin){
                detailRegionCode = memberDTO.getDtlRegion();
            }else{
                log.error("지역코드 입력에러 - 지역코드,세부지역코드 없음 : 입력된 지역코드 = {} , 입력된 세부지역코드 = {}", regionCode, detailRegionCode);
                return "redirect:/";
            }
        }
        model.addAttribute("licenseBookInfo", m_searchService.getLicenseBookList(licenseCode, regionCode, detailRegionCode, pageNum, bookCount));
        model.addAttribute("region", regionCode);
        model.addAttribute("detailRegion", detailRegionCode);
        return "search/book-template";
    }

    @GetMapping("/subject-book-list")
    public String getSubjectBookList(@RequestParam("subjectCode") int subjectCode,
                                     @RequestParam("pageNum") int pageNum,
                                     @RequestParam("bookCount") int bookCount, Model model){

        SubjectBookListDTO resultDTO = m_searchService.getSubjectBookList(subjectCode, pageNum,  bookCount);

        model.addAttribute("subjectBookInfo", resultDTO);
        return "search/subject-book-template";
    }

    @GetMapping("/book-detail")
    public String bookDetailInfo(@RequestParam("isbn") Long isbn,
                                 @RequestParam(value = "region", required = false) short region,
                                 @RequestParam(value = "detailRegion",  required = false) int detailRegion,
                                 Model model, HttpServletRequest servletRequest){
        if(region < 0){
            log.error("지역코드 입력에러 - 필수입력 지역코드가 없음 : 입력된 지역코드 = {}", region);
            return "redirect:/";
        }
        if(region < 0 && detailRegion < 0){
            log.error("지역코드 입력에러 - 지역코드,세부지역코드 2개 다 없음 : 입력된 지역코드 = {} , 입력된 세부지역코드 = {}", region, detailRegion);
            return "redirect:/";
        }
        HttpSession session = servletRequest.getSession(false);
        if(Objects.nonNull(session)){
            Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
            model.addAttribute("memberId", memberId);
        }
        model.addAttribute("bookDetailInfo", m_searchService.getBookDetailInfo(isbn, region, detailRegion));
        return "search/book-detail";
    }

    @GetMapping("/playlist")
    public String searchPlayList(@RequestParam("keywordCode") int keywordCode,
                                 @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                                 @RequestParam("pageNum") int pageNum,
                                 @RequestParam("keywordType") KEYWORD_TYPE keywordType,
                                 Model model){
        if(keywordCode < 0 || pageNum <= 0){
            log.error("유튜브검색 파라미터 입력 에러 : 입력된 keywordCode, pageNum, keywordType 값 = {},{},{]", keywordCode, pageNum, keywordType);
            return "redirect:/";
        }
        model.addAttribute("searchResult", m_searchService.searchPlayList(keywordCode, searchKeyword, pageNum, YOUTUBE_PAGING_INFO.YOUTUBE_COUNT_PER_PAGE, keywordType));
        return "search/playlist-template";
    }

    @GetMapping("/keyword-book-list")
    public String getKeywordBookList(@RequestParam("keyword") String keyword,
                                     @RequestParam("pageNum") int pageNum,
                                     @RequestParam("bookCount") int bookCount, Model model){
        SubjectBookListDTO bookListDTO = m_searchService.getSubjectBookList(keyword, pageNum, bookCount);
        model.addAttribute("subjectBookInfo", bookListDTO);
        return "search/subject-book-template";
    }
}
