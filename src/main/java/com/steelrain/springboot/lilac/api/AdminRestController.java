package com.steelrain.springboot.lilac.api;

import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.rest.BaseRestAPIResponse;
import com.steelrain.springboot.lilac.service.IAdminService;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/api")
public class AdminRestController {

    private final IAdminService m_adminService;


    @PostMapping("/add-pl")
    public ResponseEntity<AddCandiPlayListResponse> addCandiPlayList(@RequestBody AddCandiPlayListRequest request, HttpSession session){
        if(session.getAttribute(SESSION_KEY.MEMBER_ID) == null){
            return new ResponseEntity<>(AddCandiPlayListResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("로그인이 필요한 서비스 입니다")
                    .build(), HttpStatus.UNAUTHORIZED);
        }
        m_adminService.addCandiPlayList(request.playListId);
        return new ResponseEntity<>(AddCandiPlayListResponse.builder()
                .requestParameter(request)
                .code(HttpStatus.OK.value())
                .message("추천재생목록 후보를 등록하였습니다")
                .build(), HttpStatus.OK);
    }

    @SuperBuilder
    public static class AddCandiPlayListResponse extends BaseRestAPIResponse {
    }

    @Getter
    public static class AddCandiPlayListRequest{
        private Long playListId;
    }
}
