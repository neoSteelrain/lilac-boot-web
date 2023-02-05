package com.steelrain.springboot.lilac.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberRegistrationEvent {
    private Long memberId;
    private String memberNickname;
}
