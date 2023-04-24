package com.steelrain.springboot.lilac.api;

import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoProgressDTO;
import com.steelrain.springboot.lilac.datamodel.rest.BaseRestAPIResponse;
import com.steelrain.springboot.lilac.service.IVideoService;
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
    public ResponseEntity<UpdateVideoPlaytimeResponse> updateVideoPlaytime(@RequestBody UpdateVideoPlaytimeRequest request, HttpSession session){
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        Integer grade = (Integer) session.getAttribute(SESSION_KEY.MEMBER_GRADE);
        if(memberId == null){
            return new ResponseEntity<>(UpdateVideoPlaytimeResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("로그인이 필요한 서비스 입니다")
                    .build(), HttpStatus.UNAUTHORIZED);
        }
        if(memberId != null && grade == 1){
            return new ResponseEntity<>(UpdateVideoPlaytimeResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                    .message("관리자는 사용할 수 없는  서비스 입니다")
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
        YoutubeVideoProgressDTO progressDTO = m_videoService.updateVideoPlaytime(request.lectureVideoId, request.playtime);
        return new ResponseEntity<>(UpdateVideoPlaytimeResponse.builder()
                .requestParameter(request)
                .code(HttpStatus.OK.value())
                .message("재생시간을 업데이트하였습니다")
                .progressRate(progressDTO.getProgressRate())
                .videoId(progressDTO.getVideoId())
                .build(), HttpStatus.OK);
    }

    @PostMapping("/like-video")
    public ResponseEntity<LikeVideoResponse> updateLikeVideo(@RequestBody LikeVideoRequest request, HttpSession session){
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        Integer grade = (Integer) session.getAttribute(SESSION_KEY.MEMBER_GRADE);
        if(memberId == null){
            return new ResponseEntity<>(LikeVideoResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("로그인이 필요한 서비스 입니다")
                    .build(), HttpStatus.UNAUTHORIZED);
        }
        if(memberId != null && grade == 1){
            return new ResponseEntity<>(LikeVideoResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                    .message("관리자는 사용할 수 없는  서비스 입니다")
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
        Map<String, Long> res = m_videoService.updateLikeVideo(request.videoId, memberId);

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
        Long memberId = (Long) session.getAttribute(SESSION_KEY.MEMBER_ID);
        Integer grade = (Integer) session.getAttribute(SESSION_KEY.MEMBER_GRADE);
        if(memberId == null){
            return new ResponseEntity<>(DisLikeVideoResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("로그인이 필요한 서비스 입니다")
                    .build(), HttpStatus.UNAUTHORIZED);
        }
        if(memberId != null && grade == 1){
            return new ResponseEntity<>(DisLikeVideoResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                    .message("관리자는 사용할 수 없는  서비스 입니다")
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }

        Map<String, Long> res = m_videoService.updateDislikeVideo(request.videoId, memberId);
        return new ResponseEntity<>(DisLikeVideoResponse.builder()
                .requestParameter(request)
                .code(HttpStatus.OK.value())
                .message("싫어요 처리완료")
                .likeCount(res.get("lilac_like_count"))
                .dislikeCount(res.get("lilac_dislike_count"))
                .build(), HttpStatus.OK);
    }

    @Getter
    static class UpdateVideoPlaytimeRequest {
        // ref_tbl_lecture_youtube 테이블의 id 값
        private Long lectureVideoId;
        private Long playtime;
    }

    @Getter
    @SuperBuilder
    static class UpdateVideoPlaytimeResponse extends BaseRestAPIResponse {
        private String videoId;
        private double progressRate;
    }

    @Getter
    @SuperBuilder
    static class LikeVideoResponse extends BaseRestAPIResponse {
        private Long likeCount;
        private Long dislikeCount;
    }

    @Getter
    @SuperBuilder
    static class DisLikeVideoResponse extends BaseRestAPIResponse{
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
