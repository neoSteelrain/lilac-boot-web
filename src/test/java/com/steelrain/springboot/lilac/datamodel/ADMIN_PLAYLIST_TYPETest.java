package com.steelrain.springboot.lilac.datamodel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ADMIN_PLAYLIST_TYPETest {

    @Test
    @DisplayName("ADMIN_PLAYLIST_TYPE 테스트")
    public void testADMIN_PLAYLIST_TYPE(){
        ADMIN_PLAYLIST_TYPE res = ADMIN_PLAYLIST_TYPE.of("3");
        assertThat(res == ADMIN_PLAYLIST_TYPE.MONTH).isTrue();
    }
}