package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoProgressDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteYoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedPlayListDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedVideoDTO;
import org.apache.ibatis.annotations.Delete;
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
    List<YoutubeVideoDTO> findPlayListDetail(@Param("playlistId") Long youtubePlaylistId);
    List<YoutubePlayListDTO> findPlayListByKeyword(@Param("keyword") String keyword, @Param("offset") int offset, @Param("count") int count);

    @Select("SELECT count(id) FROM tbl_youtube_playlist WHERE MATCH(title) AGAINST (#{keyword})")
    int selectTotalPlayListCountByKeyword(@Param("keyword") String keyword);

    @Select("SELECT id, video_id, title, publish_date, comment_count, view_count, like_count, description, lilac_like_count, lilac_dislike_count, youtube_playlist_id FROM tbl_youtube WHERE id=#{videoId}")
    YoutubeVideoDTO findVideoDetail(@Param("videoId") Long videoId);

    @Select("SELECT id FROM tbl_youtube WHERE youtube_playlist_id=#{playListId}")
    List<Long> findAllVideoIdByPlayList(@Param("playListId") Long playListId);

    @Select("SELECT IF(count(id) = 1, 1, 0) FROM tbl_youtube_playlist WHERE id=#{playListId}")
    boolean isExistYoutubePlayList(@Param("playListId") Long playListId);

    int updateVideoPlaytime(@Param("id") Long id, @Param("playtime") Long playtime);

    List<LectureNoteYoutubeVideoDTO> findPlayListDetailOfLectureNote(@Param("memberId")Long memberId, @Param("youtubePlaylistId") Long youtubePlaylistId, @Param("noteId") Long noteId);

    String findDuration(@Param("lectureVideoId") Long lectureVideoId);

    @Select("SELECT ifnull(progress, 0) FROM ref_tbl_lecture_youtube WHERE id=#{lectureVideoId}")
    long findProgress(@Param("lectureVideoId") Long lectureVideoId);

    @Select("SELECT like_status FROM tbl_youtube_like WHERE member_id=#{memberId} AND youtube_id=#{videoId}")
    Optional<Boolean> findVideoLikeStatus(@Param("memberId") Long memberId, @Param("videoId") Long videoId);

    void setLikeStatus(@Param("memberId") Long memberId, @Param("videoId")Long videoId, @Param("likeStatus") boolean likeStatus);

    void increaseLikeCount(@Param("videoId") Long videoId);

    void updateLikeVideo(@Param("memberId") Long memberId, @Param("videoId") Long videoId, @Param("likeStatus") boolean likeStatus);

    void decreaseDislikeCount(@Param("videoId") Long videoId);

    void increaseDislikeCount(@Param("videoId") Long videoId);

    void decreaseLikeCount(@Param("videoId") Long videoId);

    Map<String, Long> selectLikeCountMap(@Param("videoId") Long videoId);

    @Delete("DELETE FROM tbl_youtube_like WHERE member_id=#{memberId} AND youtube_id=#{videoId}")
    void deleteLikeVideo(@Param("memberId") Long memberId,@Param("videoId") Long videoId);

    int selectTotalPlayListCount(@Param("id")int id, @Param("idType") int idType);

    List<YoutubePlayListDTO> findPlayListById(@Param("id")int id, @Param("idType") int idType, @Param("offset") int pageNum, @Param("count") int playlistCount);

    YoutubeVideoProgressDTO findVideoProgress(@Param("lectureVideoId") Long lectureVideoId);
}
