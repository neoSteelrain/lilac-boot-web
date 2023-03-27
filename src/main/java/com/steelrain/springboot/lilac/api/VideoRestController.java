package com.steelrain.springboot.lilac.api;

import com.steelrain.springboot.lilac.service.IVideoService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.debug("request.id : {}", request.lectureVideoId);
        log.debug("request.playtime : {}", request.playtime);
        boolean isUpdated = m_videoService.updateVideoPlaytime(request.lectureVideoId, request.playtime);
        return new ResponseEntity<>(UpdateVideoPlaytimeResponse.builder()
                .requestParameter(request)
                .code(HttpStatus.OK.value())
                .message("재생시간을 업데이트하였습니다")
                .build(), HttpStatus.OK);
    }

    @Getter
    static class UpdateVideoPlaytimeRequest {
        // ref_tbl_lecture_youtube 테이블의 id 값
        private Long lectureVideoId;
        private Long playtime;
    }

    @Getter
    @Builder
    static class UpdateVideoPlaytimeResponse {
        private Object requestParameter;
        private int code;
        private String message;
        private String status;
    }
}
