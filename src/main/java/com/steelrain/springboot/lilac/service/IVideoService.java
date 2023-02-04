package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.RecommendedVideoDTO;
import com.steelrain.springboot.lilac.datamodel.VideoPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;

import java.util.List;

public interface IVideoService {
    List<RecommendedVideoDTO> getRecommendedVideoList();

    List<YoutubeVideoDTO> getPlayListDetail(Long youtubePlaylistId);

    VideoPlayListSearchResultDTO searchPlayList(String keyword, int offset, int count);
}
