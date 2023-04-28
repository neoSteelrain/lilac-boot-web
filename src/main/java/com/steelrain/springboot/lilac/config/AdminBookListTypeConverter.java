package com.steelrain.springboot.lilac.config;

import com.steelrain.springboot.lilac.datamodel.ADMIN_BOOKLIST_TYPE;
import org.springframework.core.convert.converter.Converter;

public class AdminBookListTypeConverter implements Converter<String, ADMIN_BOOKLIST_TYPE> {
    @Override
    public ADMIN_BOOKLIST_TYPE convert(String source) {
        return ADMIN_BOOKLIST_TYPE.of(source);
    }
}
