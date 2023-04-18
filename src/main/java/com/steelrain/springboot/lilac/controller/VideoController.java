package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.common.SESSION_KEY;
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

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 동영상 관련 기능을 담당하는 컨트롤러
 * 유튜브 정보를 담당한다
 * 재생목록 플레이 페이지는 강의노트, 관리자, 일반 재생 페이지로 분리한다
 * - 분리하는 이유 : 각각의 목적에 맞게 수정하기 위해서 분리한다
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/video")
public class VideoController {

    private final VideoService m_videoService;


    /**
     * 강의노트, 관리자 페이지를 제외한 페이지에서 호출하는 영상플레이 페이지를 담당한다
     * @param youtubePlaylistId 재생하려는 재생목록의 ID
     * @param model
     * @param session
     * @return 재생목록 플레이 페이지
     */
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
        model.addAttribute("isLikeVideo", videoDTOList.size() > 0 ? m_videoService.getLikeStatus(memberId,  videoDTOList.get(0).getId()) : null);
        model.addAttribute("memberGrade", session.getAttribute(SESSION_KEY.MEMBER_GRADE));
        return "/video/playlist-detail";
    }

    /**
     * 관리자 페이지에서 호출하는 영상플레이 페이지를 담당한다
     * @param plId 재생하려는 재생목록의 ID
     * @param model
     * @param session
     * @return 재생목록 플레이 페이지
     */
    @GetMapping("/admin-playlist-detail")
    public String getAdminPlayListDetail(@RequestParam("plId") Long plId, Model model, HttpSession session){
        List<YoutubeVideoDTO> videoDTOList = m_videoService.getPlayListDetail(plId);
        model.addAttribute("videoList", videoDTOList);
        model.addAttribute("playListId", plId);
        model.addAttribute("isLikeVideo", null);
        model.addAttribute("memberGrade", session.getAttribute(SESSION_KEY.MEMBER_GRADE));
        return "/video/playlist-detail";
    }

    /**
     * 강의노트 페이지에서 호출하는 영상플레이 페이지를 담당한다
     * @param youtubePlaylistId 재생하려는 재생목록의 ID
     * @param model
     * @param session
     * @return 재생목록 플레이 페이지
     */
    @GetMapping("/lec-playlist-detail")
    public String getPlayListDetailOfLectureNote(@RequestParam("youtubePlaylistId") Long youtubePlaylistId,
                                                 @RequestParam("noteId") Long noteId,
                                                 Model model, HttpSession session){
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        model.addAttribute("memberId", memberId);
        List<LectureNoteYoutubeVideoDTO> videoDTOList = m_videoService.getPlayListDetailOfLectureNote(memberId, youtubePlaylistId, noteId);
        model.addAttribute("videoList", videoDTOList);
        model.addAttribute("playListId", youtubePlaylistId);
        model.addAttribute("isLikeVideo", videoDTOList.size() > 0 ? m_videoService.getLikeStatus(memberId,  videoDTOList.get(0).getId()) : null);
        model.addAttribute("memberGrade", session.getAttribute(SESSION_KEY.MEMBER_GRADE));
        return "/video/lecture-note-play";
    }

    /**
     * 유튜브영상 하단의 영상정보 페이지를 담당한다
     * @param videoId 재생하려는 영상의 ID
     * @param model
     * @param session
     * @return 유튜브영상 정보 HTML 
     */
    @GetMapping("/video-template")
    public String getVideoTemplate(@RequestParam("videoId") Long videoId, Model model, HttpSession session){
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        model.addAttribute("memberId", memberId); // 로그인 중이면 '강의노트추가' 메뉴가 보인다. 로그인중이 아니면 null 로 설정해서 '강의노트추가' 메뉴가 보이지 않는다
        YoutubeVideoDTO videoDTO = m_videoService.getVideoDetail(videoId);
        model.addAttribute("videoInfo", videoDTO);
        model.addAttribute("playListId", videoDTO.getYoutubePlaylistId());
        model.addAttribute("isLikeVideo", m_videoService.getLikeStatus(memberId, videoId));
        model.addAttribute("memberGrade", session.getAttribute(SESSION_KEY.MEMBER_GRADE));
        return "/video/video-play-template";
    }
}
