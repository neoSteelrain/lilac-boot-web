package com.steelrain.springboot.lilac.api;

import com.steelrain.springboot.lilac.common.SESSION_KEY;
import com.steelrain.springboot.lilac.datamodel.rest.BaseRestAPIResponse;
import com.steelrain.springboot.lilac.service.IAdminBookService;
import com.steelrain.springboot.lilac.service.IAdminPlayListService;
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

    private final IAdminPlayListService m_adminPlService;
    private final IAdminBookService m_adminBookService;

    @PostMapping("/add-candi-pl")
    public ResponseEntity<AddCandiPlayListResponse> addCandiPlayList(@RequestBody AddCandiPlayListRequest request, HttpSession session){
        if(session.getAttribute(SESSION_KEY.MEMBER_ID) == null){
            return new ResponseEntity<>(AddCandiPlayListResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("로그인이 필요한 서비스 입니다")
                    .build(), HttpStatus.UNAUTHORIZED);
        }
        m_adminPlService.addCandiPlayList(request.playListId);
        return new ResponseEntity<>(AddCandiPlayListResponse.builder()
                .requestParameter(request)
                .code(HttpStatus.OK.value())
                .message("추천재생목록 후보를 등록하였습니다")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/add-candi-book")
    public ResponseEntity<AddCandiBookResponse> addCandiBook(@RequestBody AddCandiBookRequest request, HttpSession session){
        if(session.getAttribute(SESSION_KEY.MEMBER_ID) == null){
            return new ResponseEntity<>(AddCandiBookResponse.builder()
                    .requestParameter(request)
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("로그인이 필요한 서비스 입니다")
                    .build(), HttpStatus.UNAUTHORIZED);
        }
        m_adminBookService.addCandiBook(request.bookId);
        return new ResponseEntity<>(AddCandiBookResponse.builder()
                .requestParameter(request)
                .code(HttpStatus.OK.value())
                .message("추천재생목록 후보를 등록하였습니다")
                .build(), HttpStatus.OK);
    }


    @SuperBuilder
    public static class AddCandiBookResponse extends BaseRestAPIResponse{

    }


    @Getter
    public static class AddCandiBookRequest{
        private Long bookId;
    }

    @SuperBuilder
    public static class AddCandiPlayListResponse extends BaseRestAPIResponse {
    }

    @Getter
    public static class AddCandiPlayListRequest{
        private Long playListId;
    }
}
