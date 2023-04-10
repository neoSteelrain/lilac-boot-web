package com.steelrain.springboot.lilac.api;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.MemberDTO;
import com.steelrain.springboot.lilac.service.IVideoService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/video/api")
public class VideoRestController {
    private final IVideoService m_videoService;

    /**
     * 유튜브동영상의 재생시간을 받아서 회원의 강의노트 영상의 재생시간으로 업데이트한다
     * @param request
     * @return
     */
    @PostMapping("/update-playtime")
    public ResponseEntity<UpdateVideoPlaytimeResponse> updateVideoPlaytime(@RequestBody UpdateVideoPlaytimeRequest request){
        boolean isUpdated = m_videoService.updateVideoPlaytime(request.lectureVideoId, request.playtime);
        return new ResponseEntity<>(UpdateVideoPlaytimeResponse.builder()
                .requestParameter(request)
                .code(HttpStatus.OK.value())
                .message("재생시간을 업데이트하였습니다")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/like-video")
    public ResponseEntity<LikeVideoResponse> updateLikeVideo(@RequestBody LikeVideoRequest request, HttpSession session){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(memberDTO == null){
            return new ResponseEntity<>(LikeVideoResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("로그인이 필요한 서비스 입니다")
                    .build(), HttpStatus.UNAUTHORIZED);
        }
        Map<String, Long> res = m_videoService.updateLikeVideo(request.videoId, memberDTO.getId());

        return new ResponseEntity<>(LikeVideoResponse.builder()
                .requestParameter(request)
                .code(HttpStatus.OK.value())
                .message("좋아요 처리완료")
                .likeCount(res.get("lilac_like_count"))
                .dislikeCount(res.get("lilac_dislike_count"))
                .build(), HttpStatus.OK);
    }

    @PostMapping("/dislike-video")
    public ResponseEntity<DisLikeVideoResponse> updateDislikeVideo(@RequestBody DislikeVideoRequest request, HttpSession session){
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(SESSION_KEY.LOGIN_MEMBER);
        if(memberDTO == null){
            return new ResponseEntity<>(DisLikeVideoResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("로그인이 필요한 서비스 입니다")
                    .build(), HttpStatus.UNAUTHORIZED);
        }
        Map<String, Long> res = m_videoService.updateDislikeVideo(request.videoId, memberDTO.getId());
        return new ResponseEntity<>(DisLikeVideoResponse.builder()
                .requestParameter(request)
                .code(HttpStatus.OK.value())
                .message("싫어요 처리완료")
                .likeCount(res.get("lilac_like_count"))
                .dislikeCount(res.get("lilac_dislike_count"))
                .build(), HttpStatus.OK);
    }

    @Getter
    @SuperBuilder
    static class BaseResponse{
        protected Object requestParameter;
        protected int code;
        protected String message;
        protected String status;
    }

    @Getter
    static class UpdateVideoPlaytimeRequest {
        // ref_tbl_lecture_youtube 테이블의 id 값
        private Long lectureVideoId;
        private Long playtime;
    }

    @Getter
    @SuperBuilder
    static class UpdateVideoPlaytimeResponse extends BaseResponse {
    }

    @Getter
    @SuperBuilder
    static class LikeVideoResponse extends BaseResponse {
        private Long likeCount;
        private Long dislikeCount;
    }

    @Getter
    @SuperBuilder
    static class DisLikeVideoResponse extends BaseResponse{
        private Long likeCount;
        private Long dislikeCount;
    }

    @Getter
    static class LikeVideoRequest {
        private Long videoId;
    }

    @Getter
    static class DislikeVideoRequest {
        private Long videoId;
    }
}
