package com.steelrain.springboot.lilac.config;

import com.steelrain.springboot.lilac.datamodel.ADMIN_BOOKLIST_TYPE;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
class AdminBookListTypeConverterTest {
    
    @Test
    @DisplayName("AdminBookListTypeConverter 테스트")
    public void testAdminBookListTypeConverter(){
        ADMIN_BOOKLIST_TYPE type = ADMIN_BOOKLIST_TYPE.valueOf("ALL");
        ADMIN_BOOKLIST_TYPE type2 = ADMIN_BOOKLIST_TYPE.of("1");
        log.debug("type2 : {}", type2);
        assertThat(type2 == ADMIN_BOOKLIST_TYPE.ALL).isTrue();
    }
}