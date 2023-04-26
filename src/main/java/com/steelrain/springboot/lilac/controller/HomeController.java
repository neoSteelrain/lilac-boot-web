package com.steelrain.springboot.lilac.controller;


import com.steelrain.springboot.lilac.common.ICacheService;
import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.service.IBookService;
import com.steelrain.springboot.lilac.service.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ICacheService m_keywordCategoryCacheService;
    private final IVideoService m_videoService;
    private final IBookService m_bookService;


    @GetMapping("/")
    public String index(Model model, HttpServletRequest request){
        model.addAttribute("subjectCodes",m_keywordCategoryCacheService.getSubjectCodeList());
        model.addAttribute("libRegionCodes",m_keywordCategoryCacheService.getLibraryRegionCodeList());
        model.addAttribute("licenseCodes",m_keywordCategoryCacheService.getLicenseCodeList());
        model.addAttribute("recommendedPlayList", m_videoService.getRecommendedPlayList());
        model.addAttribute("recommendedBookList", m_bookService.getRecommendedBookList());
        model.addAttribute("isLogin", Objects.isNull(request.getSession().getAttribute(SESSION_KEY.MEMBER_ID)) ? "0":"1");
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
