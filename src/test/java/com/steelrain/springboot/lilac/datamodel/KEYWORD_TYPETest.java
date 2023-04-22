package com.steelrain.springboot.lilac.datamodel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class KEYWORD_TYPETest {

    @Test
    @DisplayName("키워드 문자열 파싱 테스트")
    public void testKeywordValueOf(){
        KEYWORD_TYPE res1 = Arrays.stream(KEYWORD_TYPE.values()).filter(val -> val.equals("2")).findFirst().orElse(KEYWORD_TYPE.NONE);
        KEYWORD_TYPE res2 = Arrays.stream(KEYWORD_TYPE.values()).filter(val -> val.getValue() == Integer.parseInt("3")).findFirst().orElse(KEYWORD_TYPE.NONE);
        assertThat(res2 == KEYWORD_TYPE.NONE).isTrue();
        log.debug("res1 : "+res1);
        log.debug("res2 : "+res2);
    }
}