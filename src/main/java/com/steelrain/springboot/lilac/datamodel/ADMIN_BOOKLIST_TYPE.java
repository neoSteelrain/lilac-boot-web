package com.steelrain.springboot.lilac.datamodel;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum ADMIN_BOOKLIST_TYPE {
    NONE(0), ALL(1), TODAY(2), WEEK(3), MONTH(4);

    private int m_blType;
    ADMIN_BOOKLIST_TYPE(int blType){
        m_blType = blType;
    }

    public int getValue(){
        return m_blType;
    }
    public static ADMIN_BOOKLIST_TYPE of(String type){
        if(!StringUtils.isNumeric(type)){
            throw new IllegalArgumentException(String.format("변환할 수 없는 type값입니다. 입력된 type값 - %s", type));
        }
        return Arrays.stream(ADMIN_BOOKLIST_TYPE.values()).filter(val -> val.getValue() == Integer.parseInt(type)).findFirst().orElse(ADMIN_BOOKLIST_TYPE.NONE);
    }
}
