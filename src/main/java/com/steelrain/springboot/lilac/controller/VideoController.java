package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 동영상 관련 기능을 담당하는 컨트롤러
 * 유튜브 정보를 담당한다
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/video")
public class VideoController {

    private final VideoService m_videoService;


    @GetMapping("/playlist-detail")
    public String getPlayListDetail(@RequestParam("youtubePlaylistId") Long youtubePlaylistId, Model model){
        List<YoutubeVideoDTO> videoDTO = m_videoService.getPlayListDetail(youtubePlaylistId);
        model.addAttribute("videoInfo", videoDTO);
        return "/video/playlist-detail";
    }
}
