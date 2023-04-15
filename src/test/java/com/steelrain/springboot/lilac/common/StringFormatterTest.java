package com.steelrain.springboot.lilac.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@Slf4j
class StringFormatterTest {

    @Test
    void 자격증시험일정_문자열_포맷팅_정상_테스트() {
        Optional<String> result = StringFormatter.toFormattedDateString("20230321");

        assertThat(result.isPresent()).isTrue();
        assertThat("2023-03-21".equals(result.get())).isTrue();
    }

    @Test
    void 자격증시험일정_문자열_포맷팅_비정상_테스트() {
        Optional<String> result = StringFormatter.toFormattedDateString("fdsfsdfsdfsdfsd");
        //Optional<String> result = StringFormatter.toDateFormattedString("20231340");

        assertThat(result.isPresent()).isFalse();
        assertThat(result.isEmpty()).isTrue();
    }
}