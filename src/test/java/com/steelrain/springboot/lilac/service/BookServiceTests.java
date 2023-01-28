package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LicenseBookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalField;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BookServiceTests {

    @Autowired
    private IBookService bookService;

    @Test
    public void testGetKakaoBookLibraryList(){

        List<LicenseBookDTO> resultList = bookService.getLicenseBookList("정보처리기사", (short)23, 23040);

        System.out.println("================= 책정보 시작 ====================");
        resultList.stream().forEach(result ->{
            System.out.println(result.getKakaoBookDTO().toString());
        });
        System.out.println("================= 책정보 끝 ====================");
        
        System.out.println("================= 도서관 출력 시작 ====================");
        resultList.stream().forEach(result -> {
            System.out.println(String.format("도서관 갯수 : %d", result.getLibraryList().size()));
            result.getLibraryList().stream().forEach(library ->{
                System.out.println(String.format("도서관 정보 : %s", library.toString()));
            });
        });
        System.out.println("================= 도서관 출력 끝 ====================");
        // 2023-01-10T00:00:00.000+09:00
    }

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
