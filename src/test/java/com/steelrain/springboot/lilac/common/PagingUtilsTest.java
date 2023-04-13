package com.steelrain.springboot.lilac.common;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

class PagingUtilsTest {

    @Test
    void 페이징유틸테스트() {
        PageDTO res = PagingUtils.createPagingInfo(31, 1, 20);
        assertThat(Objects.nonNull(res)).isTrue();
        assertThat(res.getMaxPage() == 2).isTrue();
    }
}