package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.datamodel.AdminPlayListRequest;
import com.steelrain.springboot.lilac.datamodel.AdminPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.service.IAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final static int PAGE_COUNT = 3;
    private final IAdminService m_adminService;


    @GetMapping("/admin-menu")
    public String adminForm(Model model){
        model.addAttribute("totalPlcnt", m_adminService.getTotalPlayListCount());
        model.addAttribute("todayPlcnt", m_adminService.getTodayPlayListCount());
        model.addAttribute("weekPlcnt", m_adminService.getWeekPlayListCount());
        model.addAttribute("monthPlcnt", m_adminService.getMonthPlayListCount());
        return "admin/admin-menu";
    }

    @PostMapping("/admin-playlist-template")
    public String getAdminPlayListTemplate(@ModelAttribute AdminPlayListRequest request, Model model){
        /*
            - type 값 분류
            1: 모든 재생목록
            2: 오늘 추가된 재생목록
            3: 1주일간 추가된 재생목록
            4: 1달간 추가된 재생목록
            5: 많이 추천된 재생목록
            6: 가장 많이본 재생목록
            7: 가장 적게본 재생목록
         */
        prePatch(request);
        AdminPlayListSearchResultDTO plInfo = null;
        switch (request.getPlType()){
            case 1:
                plInfo = m_adminService.getAllPlayList(request.getPageNum(), PAGE_COUNT, request.getLicenseIds(), request.getSubjectIds());
                break;
            case 2:
                plInfo = m_adminService.getTodayPlayList(request.getPageNum(), PAGE_COUNT, request.getLicenseIds(), request.getSubjectIds());
                break;
            case 3:
                plInfo = m_adminService.getWeekPlayList(request.getPageNum(), PAGE_COUNT, request.getLicenseIds(), request.getSubjectIds());
                break;
            case 4:
                plInfo = m_adminService.getMonthPlayList(request.getPageNum(), PAGE_COUNT, request.getLicenseIds(), request.getSubjectIds());
                break;
            default:
                break;
        }
        model.addAttribute("plInfo", plInfo);
        return "admin/playlist-template";
    }

    @GetMapping("/candi-pl")
    public String candiPlayListTemplate(Model model){
        model.addAttribute("cpl", m_adminService.getCandiPlayList());
        return "admin/candi-pl-template";
    }

    @PostMapping("/remove-candi-pl")
    public String removeCandiPlayList(@RequestParam("plId") Long playlistId, Model model){
        model.addAttribute("cpl", m_adminService.removeCandiPlayList(playlistId));
        return "admin/candi-pl-template";
    }

    @PostMapping("/update-recommend-pl")
    public String udpateRecommendPlayList(@RequestParam(value="cplArray[]") List<Long> cplList, Model model){
        model.addAttribute("rpl", m_adminService.updateRecommendPlayList(cplList));
        return "admin/recommend-pl-template";
    }

    @GetMapping("/recommend-pl")
    public String recommendPlayListTemplate(Model model){
        model.addAttribute("rpl", m_adminService.getRecommendPlayList());
        return "admin/recommend-pl-template";
    }

    @PostMapping("/remove-recommend-pl")
    public String removeRecommendPlayList(@RequestParam("plId")Long playListId, Model model){
        model.addAttribute("rpl", m_adminService.removeRecommendPlayList(playListId));
        return "admin/recommend-pl-template";
    }
    /*
        - 프런트에서 jQuery ajax로 licenseIds, subjectIds 배열을 넘길때 null 로 설정해서 보냈을때
          컨트롤러에서 null 로 받지않고 길이가 0 인 int 배열로 받아진다.
          : 아직 원인이 무엇인지 파악하지 못했으므로 일단은 길이가 0 일때 null로 세팅해준다.
     */
    private void prePatch(AdminPlayListRequest request){
        if(request.getLicenseIds() != null && request.getLicenseIds().length == 0){
            request.setLicenseIds(null);
        }
        if(request.getSubjectIds() != null && request.getSubjectIds().length == 0){
            request.setSubjectIds(null);
        }
    }
}
