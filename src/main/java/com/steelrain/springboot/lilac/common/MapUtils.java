package com.steelrain.springboot.lilac.common;

import java.util.Map;

public class MapUtils {

    private MapUtils(){}

    /**
     * WebRequest 의 파라미터맵을 파싱하기 위해 만듬
     * @param parameterMap
     * @return
     */
    public static String dumpWebRequestMapToString(Map<String, String[]> parameterMap){
        StringBuilder sb = new StringBuilder(256);
        parameterMap.forEach((key, values) -> {
            sb.append(key).append("=").append("[");
            for (String value : values) {
                sb.append(value).append(",");
            }
            sb.delete(sb.length() - 1, sb.length()).append("], ");
        });
        int length = sb.length();
        if (2 <= length){
            sb.delete(length - 2, length);
        }
        return sb.toString();
    }
}
