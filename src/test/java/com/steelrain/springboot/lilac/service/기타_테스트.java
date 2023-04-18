package com.steelrain.springboot.lilac.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class 기타_테스트 {

    @Test
    public void LongValueOf_테스트(){
        Long i = Long.valueOf(31);
        Long i2  = Long.valueOf(31);

        assertThat(i.equals(i2));
    }

    @Test
    public void ISO8601Duration_파싱테스트() throws ParseException {
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
    
    @Test
    public void UUID_테스트(){
        UUID uuid = UUID.fromString("파일이름");
        log.debug("uuid : {}", uuid.toString());
    }

    @Test
    public void hhhhhhh(){
        Long id = (Long) null;
        assertThat(Objects.isNull(id)).isTrue();
        log.debug("id : {}", id);
    }

    @Test
    public void rrrrrrrr(){
        long val = 33325;
        double tmp = val * 0.99;
        log.debug("tmp : {}", tmp);
        log.debug("long tmp : {}", (long)tmp);
    }
}
