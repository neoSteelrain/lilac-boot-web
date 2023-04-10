package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteYoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedPlayListDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedVideoDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    Optional<Boolean> findVideoLikeStatus(Long memberId, Long videoId);

    void setLikeStatus(Long memberId, Long videoId, boolean likeStatus);

    void increaseLikeCount(Long videoId);

    void updateLikeVideo(Long memberId, Long videoId, boolean likeStatus);

    void decreaseDislikeCount(Long videoId);

    void increaseDislikeCount(Long videoId);

    void decreaseLikeCount(Long videoId);

    Map<String, Long> selectLikeCountMap(Long videoId);

    void deleteLikeVideo(Long memberId, Long videoId);
}
