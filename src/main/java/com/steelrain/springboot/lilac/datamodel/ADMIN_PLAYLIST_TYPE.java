package com.steelrain.springboot.lilac.datamodel;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum ADMIN_PLAYLIST_TYPE {
    /*
        - type 값 분류
        1: 모든 재생목록
        2: 오늘 추가된 재생목록
        3: 1주일간 추가된 재생목록
        4: 1달간 추가된 재생목록
        5: 많이 추천된 재생목록
        6: 가장 많이본 재생목록
        7: 가장 적게본 재생목록
    */
    NONE(0),ALL(1), TODAY(2), WEEK(3), MONTH(4);

    private int m_plType;

    private ADMIN_PLAYLIST_TYPE(int plType){
        this.m_plType = plType;
    }

    public int getValue(){
        return m_plType;
    }

    public static ADMIN_PLAYLIST_TYPE of(String type){
        if(!StringUtils.isNumeric(type)){
            throw new IllegalArgumentException(String.format("변환할 수 없는 type값입니다. 입력된 type값 - %s", type));
        }
        return Arrays.stream(ADMIN_PLAYLIST_TYPE.values()).filter(val -> val.getValue() == Integer.parseInt(type)).findFirst().orElse(ADMIN_PLAYLIST_TYPE.NONE);
    }
}
