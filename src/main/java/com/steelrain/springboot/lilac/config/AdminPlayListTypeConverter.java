package com.steelrain.springboot.lilac.config;

import com.steelrain.springboot.lilac.datamodel.ADMIN_PLAYLIST_TYPE;
import org.springframework.core.convert.converter.Converter;

public class AdminPlayListTypeConverter implements Converter<String, ADMIN_PLAYLIST_TYPE> {
    @Override
    public ADMIN_PLAYLIST_TYPE convert(String source) {
        return ADMIN_PLAYLIST_TYPE.of(source);
    }
}
