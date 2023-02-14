package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedVideoDTO;

import java.util.List;

public interface IVideoRepository {
    List<RecommendedVideoDTO> findRecommendedVideoList();

    List<YoutubeVideoDTO> findPlayListDetail(Long youtubePlaylistId);

    List<YoutubePlayListDTO> findPlayListByKeyword(String keyword, int offset, int count);
}
