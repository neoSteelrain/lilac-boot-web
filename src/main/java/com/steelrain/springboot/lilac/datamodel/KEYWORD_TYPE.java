package com.steelrain.springboot.lilac.datamodel;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 검색하려는 키워드의 타입을 나타내는 eunm
 * -1 - 값이없음
 * 0 - 키워드 검색 , 사용자가 직접 입력하는 검색어
 * 1 - 자격증 (license) , 기사, 기능사 같은 자격증
 * 2 - 주제어 (subject) , 자바, 스프링 같은 이미 등록된 키워드들
 */
public enum KEYWORD_TYPE {
    NONE(-1), KEYWORD(0), LICENSE(1), SUBJECT(2);

    private final int m_keywordType;
    KEYWORD_TYPE(int codeType){
        this.m_keywordType = codeType;
    }

    public int getValue(){
        return m_keywordType;
    }

    public static KEYWORD_TYPE of(String id){
        if(!StringUtils.isNumeric(id)){
            throw new IllegalArgumentException(String.format("변환할 수 없는 ID값입니다. 입력된 ID값 - %s, ID가 자격증인 경우는 1, 키워드인 경우는 2의 값이어야 합니다", id));
        }
        return Arrays.stream(KEYWORD_TYPE.values()).filter(val -> val.getValue() == Integer.parseInt(id)).findFirst().orElse(KEYWORD_TYPE.NONE);
    }
}
