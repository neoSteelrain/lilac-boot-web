package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.view.LectureNoteYoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedPlayListDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedVideoDTO;
import com.steelrain.springboot.lilac.datamodel.VideoPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;

import java.util.List;

public interface IVideoService {
    List<RecommendedVideoDTO> getRecommendedVideoList();
    List<RecommendedPlayListDTO> getRecommendedPlayList();
    List<YoutubeVideoDTO> getPlayListDetail(Long youtubePlaylistId);
    VideoPlayListSearchResultDTO searchPlayList(int keywordCode, String searchKeyword, int pageNum, int playlistCount, int keywordType);
    //VideoPlayListSearchResultDTO searchPlayList(int keywordCode, String searchKeyword, int pageNum, int playlistCount, SEARCH_KEYWORD_TYPE keywordType);
    List<Long> getAllVideoIdByPlayList(Long playListId);
    YoutubeVideoDTO getVideoDetail(Long videoId);
    boolean isExistYoutubePlayList(Long playListId);
    boolean updateVideoPlaytime(Long id, Long playtime);
    List<LectureNoteYoutubeVideoDTO> getPlayListDetailOfLectureNote(Long memberId, Long youtubePlaylistId);
}
