package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.YoutubePlaylistDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoMapper {

    List<YoutubeVideoDTO> findRecommendedVideoList();

    List<YoutubeVideoDTO> findPlayListDetail(Long youtubePlaylistId);

    List<YoutubePlaylistDTO> findPlayListByKeyword(String keyword);
}
