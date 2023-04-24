package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.KEYWORD_TYPE;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoProgressDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteYoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedPlayListDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedVideoDTO;
import com.steelrain.springboot.lilac.datamodel.VideoPlayListSearchResultDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;

import java.util.List;
import java.util.Map;

public interface IVideoService {
    List<RecommendedVideoDTO> getRecommendedVideoList();
    List<RecommendedPlayListDTO> getRecommendedPlayList();
    List<YoutubeVideoDTO> getPlayListDetail(Long youtubePlaylistId);
    VideoPlayListSearchResultDTO searchPlayListByKeyword(String searchKeyword, int pageNum, int playlistCount);
    VideoPlayListSearchResultDTO searchPlayListById(int keywordCode, int pageNum, int playlistCount, KEYWORD_TYPE keywordType);
    List<Long> getAllVideoIdByPlayList(Long playListId);
    YoutubeVideoDTO getVideoDetail(Long videoId);
    boolean isExistYoutubePlayList(Long playListId);
    YoutubeVideoProgressDTO updateVideoPlaytime(Long id, Long playtime);
    List<LectureNoteYoutubeVideoDTO> getPlayListDetailOfLectureNote(Long memberId, Long youtubePlaylistId, Long noteId);

    Map<String, Long> updateLikeVideo(Long videoId, Long memberId);

    Map<String, Long> updateDislikeVideo(Long videoId, Long id);

    Boolean getLikeStatus(Long memberId, Long videoId);
}
