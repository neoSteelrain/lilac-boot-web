package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteYoutubeVideoDTO;
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
    public String getPlayListDetail(@RequestParam("youtubePlaylistId") Long youtubePlaylistId, Model model, HttpSession session){
        /*
            유튜브영상 재생페이지에서 영상들을 강의노트에 추가하기 위해서는 memberId 가 필요하다.
            때문에 로그인 한 경우에는 memberId를 페이지의 hidden필드에 저장하기 위해 model에 넣어준다.
         */
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        model.addAttribute("memberId", memberId);
        List<YoutubeVideoDTO> videoDTOList = m_videoService.getPlayListDetail(youtubePlaylistId);
        model.addAttribute("videoList", videoDTOList);
        model.addAttribute("playListId", youtubePlaylistId);
        model.addAttribute("isLikeVideo", m_videoService.getLikeStatus(memberId,  videoDTOList.get(0).getId()));
        return "/video/playlist-detail";
    }

    @GetMapping("/admin-playlist-detail")
    public String getAdminPlayListDetail(@RequestParam("plId") Long plId, Model model){
        List<YoutubeVideoDTO> videoDTOList = m_videoService.getPlayListDetail(plId);
        model.addAttribute("videoList", videoDTOList);
        model.addAttribute("playListId", plId);
        model.addAttribute("isLikeVideo", null);
        return "/video/playlist-detail";
    }

    @GetMapping("/lec-playlist-detail")
    public String getPlayListDetailOfLectureNote(@RequestParam("youtubePlaylistId") Long youtubePlaylistId, Model model, HttpSession session){
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        model.addAttribute("memberId", memberId);
        List<LectureNoteYoutubeVideoDTO> videoDTOList = m_videoService.getPlayListDetailOfLectureNote(memberId, youtubePlaylistId);
        model.addAttribute("videoList", videoDTOList);
        model.addAttribute("playListId", youtubePlaylistId);
        return "/video/lecture-note-play";
    }

    @GetMapping("/video-template")
    public String getVideoTemplate(@RequestParam("videoId") Long videoId, Model model, HttpSession session){
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        YoutubeVideoDTO videoDTO = m_videoService.getVideoDetail(videoId);
        model.addAttribute("videoInfo", videoDTO);
        model.addAttribute("isLikeVideo", m_videoService.getLikeStatus(memberId, videoId));
        return "/video/video-play-template";
    }
}
