package com.steelrain.springboot.lilac.common;

import org.springframework.cglib.core.Local;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateUtils {
    private final static String ZONEID = "Asia/Seoul";
    private final static String DAY_PATTERN = "yyyy-MM-dd 23:59:59";
    public static String getTodayDateString(){
        return LocalDate.now(ZoneId.of(ZONEID)).format(DateTimeFormatter.ofPattern(DAY_PATTERN));
    }

    public static String getYesterdayString(){
        return LocalDate.now(ZoneId.of(ZONEID)).minusDays(1L).format(DateTimeFormatter.ofPattern(DAY_PATTERN));
    }

    public static String getMondayOfWeekString(){
        return LocalDate.now(ZoneId.of(ZONEID)).with(DayOfWeek.MONDAY).format(DateTimeFormatter.ofPattern(DAY_PATTERN));
    }

    public static String getSundayOfWeekString(){
        return LocalDate.now(ZoneId.of(ZONEID)).with(DayOfWeek.SUNDAY).format(DateTimeFormatter.ofPattern(DAY_PATTERN));
    }

    public static String getFirstdayOfMonth(){
        return LocalDate.now(ZoneId.of(ZONEID)).with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ofPattern(DAY_PATTERN));
    }

    public static String getLastdayOfMonth(){
        return LocalDate.now(ZoneId.of(ZONEID)).with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern(DAY_PATTERN));
    }
}
