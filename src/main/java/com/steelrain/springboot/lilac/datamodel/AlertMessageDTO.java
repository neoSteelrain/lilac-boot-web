package com.steelrain.springboot.lilac.datamodel;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 사용자에게 확인창을 보여주고 다른주소로 redirect 하기 위한 클래스
 */
@Getter
@Builder
public class AlertMessageDTO {
    private String message; // 사용자에게 보여줄 메시지
    private String redirectURL; // 리다이렉트할 주소
}
