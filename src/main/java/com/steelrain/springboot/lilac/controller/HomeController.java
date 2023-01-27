package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;
import com.steelrain.springboot.lilac.service.ISearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ISearchService m_searchService;

    @GetMapping("/")
    public String index(){
        //int count = memberMapper.getMemberCount();
        return "index";
    }

    @ModelAttribute("subjectCodes")
    public List<SubjectCodeDTO> getSubjectCodes(){
        return m_searchService.getSubjectCodes();
    }
}
