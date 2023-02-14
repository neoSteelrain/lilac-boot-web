package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.view.RecommendedVideoDTO;
import com.steelrain.springboot.lilac.datamodel.VideoPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;

import java.util.List;

public interface IVideoService {
    List<RecommendedVideoDTO> getRecommendedVideoList();

    List<YoutubeVideoDTO> getPlayListDetail(Long youtubePlaylistId);

    VideoPlayListSearchResultDTO searchPlayList(int keywordCode, int pageNum, int playlistCount, int keywordType);

    List<Long> getAllVideoIdByPlayList(Long playListId);

    YoutubeVideoDTO getVideoDetail(Long videoId);
    boolean isExistYoutubePlayList(Long playListId);
}
