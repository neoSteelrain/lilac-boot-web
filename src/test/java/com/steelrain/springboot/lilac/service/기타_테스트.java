package com.steelrain.springboot.lilac.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class 기타_테스트 {

    @Test
    public void testTTTTTT(){
        Long i = Long.valueOf(31);
        Long i2  = Long.valueOf(31);

        assertThat(i.equals(i2));
    }

    @Test
    public void ISO8601Duration파싱테스트() throws ParseException {
        String videoDuration = "PT1H28M57S";
        Duration du = Duration.parse(videoDuration);
        log.debug("du : " + du.toMinutes());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        Date tmp = sdf.parse(videoDuration);
//        log.debug("tmp : " + tmp); // 에러
        Date dd = new Date(du.toMillis());
        String tmp = sdf.format(dd);
        log.debug("tmp : " + tmp);

        log.debug(String.format("%d시간 %d분 %d초", du.toHoursPart(),du.toMinutesPart(), du.toSecondsPart()));
    }
}
