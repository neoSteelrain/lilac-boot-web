package com.steelrain.springboot.lilac.event;

import lombok.Builder;
import lombok.Getter;

/**
 * 회원가입 이벤트
 */
@Getter
@Builder
public class MemberRegistrationEvent {
    private Long memberId;
    private String memberNickname;
}
