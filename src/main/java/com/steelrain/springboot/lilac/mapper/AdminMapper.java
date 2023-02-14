package com.steelrain.springboot.lilac.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    int insertRecommendedPlayList(List<Long> videoIdList);

    @Select("SELECT id FROM tbl_youtube_playlist")
    List<Long> selectAllPlayListLId();
}
