package com.steelrain.springboot.lilac.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BookServiceTests {

    @Autowired
    private IBookService bookService;

    @Test
    public void testTimestamp(){
        String timeStr = "2023-01-10T00:00:00.000+09:00";
        ZonedDateTime zt = ZonedDateTime.parse(timeStr);
        System.out.println(Timestamp.valueOf(zt.toLocalDateTime()));
    }

    @Test
    public void testKakaoISBN(){
        String tmp = "1160503141 9791160503142";
        // StringUtils.tokenizeToStringArray(tmpIsbn, " ")
        assertThat(StringUtils.containsWhitespace(tmp));

        String[] strs = StringUtils.tokenizeToStringArray(tmp, " ");
        assertThat(strs != null).isEqualTo(strs.length == 2);
        System.out.println(strs);
    }


}
