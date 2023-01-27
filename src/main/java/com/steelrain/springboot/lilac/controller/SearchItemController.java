package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.datamodel.LibraryDetailRegionCodeDTO;
import com.steelrain.springboot.lilac.service.ISearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchItemController {

    private final ISearchService m_searchService;


    @GetMapping("/dtlRegionCode")
    @ResponseBody
    public List<LibraryDetailRegionCodeDTO> getLibDetailRegionCodes(@RequestParam("regionCode") int regionCode){
        if(regionCode < 11){ // 지역코드는 11부터 시작한다
            log.error(String.format("유효하지 않은 지역코드 : %d ", regionCode));
            return new ArrayList<>(0);
        }
        return m_searchService.getLibDetailRegionCodes(regionCode);
    }


}
