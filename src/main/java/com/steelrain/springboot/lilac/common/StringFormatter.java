package com.steelrain.springboot.lilac.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * 문자열에 관련된 포매팅기능을 제공하는 클래스
 */
public class StringFormatter {
    private static final SimpleDateFormat SIMPLE_TARGET_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    private static final SimpleDateFormat SIMPLE_SOURCE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);


    /**
     * "20220121" 같은 날짜형식의 문자열을 "2022-01-21" 날짜형식의 문자열로 바꿔준다
     * @param src 변경할 날짜형식의 문자열, "yyyyMMdd" 형식이어야 한다. 예) "2022-01-21"
     * @return "yyyy-MM-dd" 형식으로 바뀐 날짜문자열
     */
    public static Optional<String> toFormattedDateString(String src){
        if(Objects.isNull(src)){
            return Optional.empty();
        }
        try {
            Date srcDate = SIMPLE_SOURCE_DATE_FORMAT.parse(src);
            return Optional.ofNullable(SIMPLE_TARGET_DATE_FORMAT.format(srcDate));
        } catch (ParseException | RuntimeException ex) {
            return Optional.empty();
        }
    }

    /**
     * Duration 객체를 시:분:초 형식의 문자열로 변환한다
     * @param date Duration 객체
     * @return 'n시간:n분:n초' 형식의 날짜표현문자열
     */
    public static String toHMS(Duration date){
        return String.format("%d시간:%d분:%d초", date.toHoursPart(),date.toMinutesPart(), date.toSecondsPart());
    }

    /**
     * Duration 객체를 시:분 형식의 문자열로 변환환다
     * @param date Duration 객체
     * @return 'n시간:n분' 형식의 날짜표현 문자열
     */
    public static String toHM(Duration date){
        return String.format("%d시간:%d분", date.toHoursPart(), date.toMinutesPart());
    }

    /**
     * 초(second) 값을 시:분 형식의 문자열로 변환한다
     * @param seconds 시간을 초단위로 나타낸 값
     * @return 'n시간:n분' 형식의 날짜표현 문자열
     */
    public static String toHM(long seconds){
        Duration tmp = Duration.ofSeconds(seconds);
        return tmp.toHoursPart() <= 0 ? String.format("%d분", tmp.toSecondsPart()) : String.format("%d시간:%d분", tmp.toHoursPart(),tmp.toSecondsPart());
    }
}
