package com.steelrain.springboot.lilac.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class DateUtilsTest {
    private final static String ZONEID = "Asia/Seoul";
    @Test
    public void 이번주의월요일구하기(){
        String tmp = LocalDate.now(ZoneId.of(ZONEID)).with(DayOfWeek.MONDAY).toString();
        log.debug("tmp : " + tmp);
    }

    @Test
    public void 이번주의월요일일요일구하기(){
        log.debug("월요일 : {}, 일요일 : {}", DateUtils.getMondayOfWeek(), DateUtils.getSundayOfWeek());
    }

    @Test
    public void 이번달시작일마지막일구하기(){
        assertThat(DateUtils.getFirstdayOfMonth().equals("2023-04-01 23:59:59")).isTrue();
        assertThat(DateUtils.getLastdayOfMonth().equals("2023-04-30 23:59:59")).isTrue();
        log.debug("시작일 : {}, 마지막일 : {}", DateUtils.getFirstdayOfMonth(), DateUtils.getLastdayOfMonth());
    }
}