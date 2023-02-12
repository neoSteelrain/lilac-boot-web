package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String getPlayListDetail(@RequestParam("youtubePlaylistId") Long youtubePlaylistId, Model model, HttpServletRequest servletRequest){
        HttpSession session = servletRequest.getSession(false);
        /*
            유튜브영상 재생페이지에서 영상들을 강의노트에 추가하기 위해서는 memberId 가 필요하다.
            때문에 로그인 한 경우에는 memberId를 페이지의 hidden필드에 저장하기 위해 model에 넣어준다.
         */
        if(session != null && session.getAttribute(SESSION_KEY.LOGIN_MEMBER) != null){
            model.addAttribute("memberId",((MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER)).getId());
        }
        List<YoutubeVideoDTO> videoDTOList = m_videoService.getPlayListDetail(youtubePlaylistId);
        model.addAttribute("videoList", videoDTOList);
        model.addAttribute("playListId", youtubePlaylistId);
        return "/video/playlist-detail";
    }

    @GetMapping("/video-template")
    public String getVideoTemplate(@RequestParam("videoId") Long videoId, Model model){
        YoutubeVideoDTO videoDTO = m_videoService.getVideoDetail(videoId);
        model.addAttribute("videoInfo", videoDTO);
        return "/video/video-play-template";
    }
}
