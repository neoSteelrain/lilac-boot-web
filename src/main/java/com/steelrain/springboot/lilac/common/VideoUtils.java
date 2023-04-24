package com.steelrain.springboot.lilac.common;

import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * 영상관련된 유틸클래스
 */
public class VideoUtils {

    /**
     * 영상의 길이와 사용자가 재생한 재생시간을 받아 진행율을 구한다
     * @param durationStr 영상의 길이
     * @param progress 재생시간
     * @return 진행율 %값 - 소수점 1째자리까지 반환
     */
    public static double calcProgressRate(String durationStr, long progress){
        if(!StringUtils.hasText(durationStr) || progress < 0){
            throw new IllegalArgumentException(String.format("유효하지 않은 시간형식입니다. 입력된 durationStr값 : %s, progress값 : %d", durationStr, progress));
        }
        long duration = Duration.parse(durationStr).toSeconds();
        return Math.round(((double)progress / duration * 100) * 10) / 10.0; // 소수 1째 자리까지만 구한다
    }
}
