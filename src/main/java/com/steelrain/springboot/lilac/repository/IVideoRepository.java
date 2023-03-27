package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteYoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedPlayListDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedVideoDTO;

import java.util.List;

public interface IVideoRepository {
    List<RecommendedVideoDTO> findRecommendedVideoList();

    List<YoutubeVideoDTO> findPlayListDetail(Long youtubePlaylistId);

    List<YoutubePlayListDTO> findPlayListByKeyword(String keyword, int offset, int count);

    List<Long> findAllVideoIdByPlayList(Long playListId);

    YoutubeVideoDTO findVideoDetail(Long videoId);

    boolean isExistYoutubePlayList(Long playListId);

    boolean updateVideoPlaytime(Long id, Long playtime);

    List<RecommendedPlayListDTO> findRecommendedPlayList();

    int selectTotalPlayListCountByKeyword(String keywordStr);

    List<LectureNoteYoutubeVideoDTO> findPlayListDetailOfLectureNote(Long memberId, Long youtubePlaylistId);

    long getDuration(Long lectureVideoId);

    long getProgress(Long lectureVideoId);
}
