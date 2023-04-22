package com.steelrain.springboot.lilac.config;


import com.steelrain.springboot.lilac.datamodel.KEYWORD_TYPE;
import org.springframework.core.convert.converter.Converter;

public class KeywordTypeConverter implements Converter<String, KEYWORD_TYPE> {
    @Override
    public KEYWORD_TYPE convert(String source) {
        return KEYWORD_TYPE.of(source);
    }
}
