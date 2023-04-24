package com.steelrain.springboot.lilac.common;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * 날짜와 관련된 기능을 처리하는 클래스
 * - 오늘 날짜, 어제날짜, 이번주 월요일 같은 특정 날짜를 반환한다.
 * - 기준 시간대 : 서울
 * - 모든 날짜는 '년-월-일 23:59:59' 형식으로 처리한다.
 */
public class DateUtils {
    // 기준시간대
    private final static String ZONEID = "Asia/Seoul";
    
    // 반환되는 날짜 포맷
    private final static String FROM_DAY_PATTERN = "yyyy-MM-dd 00:00:00";
    private final static String TO_DAY_PATTERN = "yyyy-MM-dd 23:59:59";
    
    // 포맷터를 미리 생성해서 가지고 있는다
    private final static DateTimeFormatter M_FROM_DAY_FORMATTER = DateTimeFormatter.ofPattern(FROM_DAY_PATTERN);
    private final static DateTimeFormatter M_TO_DAY_FORMATTER = DateTimeFormatter.ofPattern(TO_DAY_PATTERN);

    /**
     * 오늘날짜를 가져온다
     * @return 오늘날짜 : 오늘이 1월 1일 이라면, 2023-01-01 23:59:59
     */
    public static PeriodDate getToday(){
        LocalDate date = LocalDate.now(ZoneId.of(ZONEID));
        return createPeriodDate(date);
    }

    /**
     * 어제 날짜를 가져온다
     * @return 어제날짜 : 어제가 1월 1일 이라면, 2023-01-01 23:59:59
     */
    public static PeriodDate getYesterday(){
        LocalDate date = LocalDate.now(ZoneId.of(ZONEID)).minusDays(1L);
        return createPeriodDate(date);
    }

    /**
     * 이번주의 월요일을 가져온다
     * @return 월요일 : 월요일이 1월 1일 이라면, 2023-01-01 23:59:59
     */
    public static PeriodDate getMondayOfWeek(){
        LocalDate date = LocalDate.now(ZoneId.of(ZONEID)).with(DayOfWeek.MONDAY);
        return createPeriodDate(date);
    }

    /**
     * 이번주의 일요일을 가져온다
     * @return 일요일 : 일요일이 1월 7일 이라면, 2023-01-07 23:59:59
     */
    public static PeriodDate getSundayOfWeek(){
        LocalDate date = LocalDate.now(ZoneId.of(ZONEID)).with(DayOfWeek.SUNDAY);
        return createPeriodDate(date);
    }

    /**
     * 이번달의 첫번째 날짜를 가져온다. 요일과는 상관없이 날짜만 반환
     * @return 첫번째 날짜 : 첫번째날이 1월 1일 이라면, 2023-01-01 23:59:59
     */
    public static PeriodDate getFirstdayOfMonth(){
        LocalDate date = LocalDate.now(ZoneId.of(ZONEID)).with(TemporalAdjusters.firstDayOfMonth());
        return createPeriodDate(date);
    }

    /**
     * 이번달의 마지말 날짜를 가져온다. 요일과는 상관없이 날짜만 반환
     * @return 마지막 날짜 : 마지막날이 1월 30일 이라면, 2023-01-30 23:59:59
     */
    public static PeriodDate getLastdayOfMonth(){
        LocalDate date = LocalDate.now(ZoneId.of(ZONEID)).with(TemporalAdjusters.lastDayOfMonth());
        return createPeriodDate(date);
    }

    /**
     * PeriodDate 생성 헬퍼메서드
     * LocalDate 의 시작/끝 날짜시간문자열을 설정해준다
     * @param date 생성하고자 하는 날짜객체
     * @return 시작/끝 날짜시간문자열을 설정된 PeriodDate 객체
     */
    private static PeriodDate createPeriodDate(LocalDate date){
        return PeriodDate.builder()
                .fromDate(date.format(M_FROM_DAY_FORMATTER))
                .toDate(date.format(M_TO_DAY_FORMATTER))
                .build();
    }

    /**
     * ISO 8601 duration 형식(P#DT#H#M#S)을 가진 시간값의 총합산을 구한다
     * 예) 1시간10분10초 + 1시간20분20초 + ... = n시n분n초 
     * @param durationList ISO 8601 duration 형식의 문자열 리스트
     * @return 합산된 duration 형식의 문자열
     */
    public static Duration getSumOfDuration(List<String> durationList) throws DateTimeParseException{
        long totalDurationValue = 0;
        for (String videoDuration : durationList) {
            totalDurationValue += Duration.parse(videoDuration).toSeconds();
        }
        return Duration.ofSeconds(totalDurationValue);
    }
}

