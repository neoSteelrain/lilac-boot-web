package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteYoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedPlayListDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedVideoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface VideoMapper {

    List<RecommendedVideoDTO> findRecommendedVideoList();
    List<RecommendedPlayListDTO> findRecommendedPlayList();
    List<YoutubeVideoDTO> findPlayListDetail(Long youtubePlaylistId);
    List<YoutubePlayListDTO> findPlayListByKeyword(@Param("keyword") String keyword, @Param("offset") int offset, @Param("count") int count);

    @Select("SELECT count(id) FROM tbl_youtube_playlist WHERE MATCH(title) AGAINST (#{keyword})")
    int selectTotalPlayListCountByKeyword(String keyword);

    @Select("SELECT id, video_id, title, publish_date, comment_count, view_count, like_count, description, lilac_like_count, lilac_dislike_count FROM tbl_youtube WHERE id=#{videoId}")
    YoutubeVideoDTO findVideoDetail(Long videoId);

    @Select("SELECT id FROM tbl_youtube WHERE youtube_playlist_id=#{playListId}")
    List<Long> findAllVideoIdByPlayList(Long playListId);

    @Select("SELECT IF(count(id) = 1, 1, 0) FROM tbl_youtube_playlist WHERE id=#{playListId}")
    boolean isExistYoutubePlayList(Long playListId);

    int updateVideoPlaytime(Long id, Long playtime);

    List<LectureNoteYoutubeVideoDTO> findPlayListDetailOfLectureNote(Long memberId, Long youtubePlaylistId);

    String findDuration(Long lectureVideoId);

    @Select("SELECT ifnull(progress, 0) FROM ref_tbl_lecture_youtube WHERE id=#{lectureVideoId}")
    long findProgress(Long lectureVideoId);

    @Select("SELECT like_status FROM tbl_youtube_like WHERE member_id=#{memberId} AND youtube_id=#{videoId}")
    Optional<Boolean> findVideoLikeStatus(Long memberId, Long videoId);

    void setLikeStatus(Long memberId, Long videoId, boolean likeStatus);

    void increaseLikeCount(Long videoId);

    void updateLikeVideo(Long memberId, Long videoId, boolean likeStatus);

    void decreaseDislikeCount(Long videoId);

    void increaseDislikeCount(Long videoId);

    void decreaseLikeCount(Long videoId);

    Map<String, Long> selectLikeCountMap(Long videoId);
}
