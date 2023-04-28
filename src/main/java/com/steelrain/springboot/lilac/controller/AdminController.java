package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.datamodel.AdminPlayListRequest;
import com.steelrain.springboot.lilac.datamodel.AdminBookListRequest;
import com.steelrain.springboot.lilac.datamodel.AdminSearchRequest;
import com.steelrain.springboot.lilac.service.IAdminBookService;
import com.steelrain.springboot.lilac.service.IAdminPlayListService;
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
    private final static int PAGE_COUNT = 10;
    private final IAdminPlayListService m_adminPlayListService;
    private final IAdminBookService m_adminBookService;


    @GetMapping("/admin-menu-pl")
    public String adminForm(Model model){
        model.addAttribute("totalPlcnt", m_adminPlayListService.getTotalPlayListCount());
        model.addAttribute("todayPlcnt", m_adminPlayListService.getTodayPlayListCount());
        model.addAttribute("weekPlcnt", m_adminPlayListService.getWeekPlayListCount());
        model.addAttribute("monthPlcnt", m_adminPlayListService.getMonthPlayListCount());
        return "admin/admin-menu-pl";
    }

    @GetMapping("/admin-menu-book")
    public String adminBookForm(Model model){
        model.addAttribute("totalBookCnt", m_adminBookService.getTotalBookCount());
        model.addAttribute("todayBookCnt", m_adminBookService.getTodayBookCount());
        model.addAttribute("weekBookCnt", m_adminBookService.getWeekBookCount());
        model.addAttribute("monthBookCnt", m_adminBookService.getMonthBookCount());
        return "admin/admin-menu-book";
    }

    @PostMapping("/admin-booklsit-template")
    public String getAdminBookListTemplate(@ModelAttribute AdminBookListRequest request, Model model){
        prePatch(request);
        model.addAttribute("blInfo", m_adminBookService.getAdminBookList(request.getBlType(), request.getPageNum(), PAGE_COUNT, request.getLicenseIds(), request.getSubjectIds()));
        return "admin/booklist-template";
    }

    @GetMapping("/candi-book")
    public String getAdminCandiBookTemplate(Model model){
        model.addAttribute("cbl", m_adminBookService.getCandiBookList());
        return "admin/candi-book-template";
    }

    @GetMapping("/recommend-book")
    public String getAdminRecommendBookTemplate(Model model){
        model.addAttribute("rbl", m_adminBookService.getRecommendBookList());
        return "admin/recommend-book-template";
    }

    @PostMapping("/remove-candi-book")
    public String removeCandiBook(@RequestParam("bookId") Long bookId, Model model){
        model.addAttribute("cbl", m_adminBookService.removeCandiBook(bookId));
        return "admin/candi-book-template";
    }

    @PostMapping("/update-recommend-book")
    public String updateRecommendBookList(@RequestParam(value = "cblArray[]") List<Long> cblList, Model model){
        model.addAttribute("rbl", m_adminBookService.updateRecommendBookList(cblList));
        return "admin/recommend-book-template";
    }

    @PostMapping("/remove-recommend-book")
    public String removeRecommendBook(@RequestParam("bookId") Long bookId, Model model){
        model.addAttribute("rbl", m_adminBookService.removeRecommendBook(bookId));
        return "admin/recommend-book-template";
    }

    @PostMapping("/admin-playlist-template")
    public String getAdminPlayListTemplate(@ModelAttribute AdminPlayListRequest request, Model model){
        prePatch(request);
        model.addAttribute("plInfo", m_adminPlayListService.getAdminPlayList(request.getPlType(), request.getPageNum(), PAGE_COUNT, request.getLicenseIds(), request.getSubjectIds()));
        return "admin/playlist-template";
    }

    @GetMapping("/candi-pl")
    public String candiPlayListTemplate(Model model){
        model.addAttribute("cpl", m_adminPlayListService.getCandiPlayList());
        return "admin/candi-pl-template";
    }

    @PostMapping("/remove-candi-pl")
    public String removeCandiPlayList(@RequestParam("plId") Long playlistId, Model model){
        model.addAttribute("cpl", m_adminPlayListService.removeCandiPlayList(playlistId));
        return "admin/candi-pl-template";
    }

    @PostMapping("/update-recommend-pl")
    public String updateRecommendPlayList(@RequestParam(value="cplArray[]") List<Long> cplList, Model model){
        model.addAttribute("rpl", m_adminPlayListService.updateRecommendPlayList(cplList));
        return "admin/recommend-pl-template";
    }

    @GetMapping("/recommend-pl")
    public String recommendPlayListTemplate(Model model){
        model.addAttribute("rpl", m_adminPlayListService.getRecommendPlayList());
        return "admin/recommend-pl-template";
    }

    @PostMapping("/remove-recommend-pl")
    public String removeRecommendPlayList(@RequestParam("plId")Long playListId, Model model){
        model.addAttribute("rpl", m_adminPlayListService.removeRecommendPlayList(playListId));
        return "admin/recommend-pl-template";
    }
    /*
        - 프런트에서 jQuery ajax로 licenseIds, subjectIds 배열을 넘길때 null 로 설정해서 보냈을때
          컨트롤러에서 null 로 받지않고 길이가 0 인 int 배열로 받아진다.
          : 아직 원인이 무엇인지 파악하지 못했으므로 일단은 길이가 0 일때 null로 세팅해준다.
     */
    private void prePatch(AdminSearchRequest request){
        if(request.getLicenseIds() != null && request.getLicenseIds().length == 0){
            request.setLicenseIds(null);
        }
        if(request.getSubjectIds() != null && request.getSubjectIds().length == 0){
            request.setSubjectIds(null);
        }
    }
}
