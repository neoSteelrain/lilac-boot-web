package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.YoutubePlayListDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteYoutubeVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedPlayListDTO;
import com.steelrain.springboot.lilac.datamodel.view.RecommendedVideoDTO;
import com.steelrain.springboot.lilac.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class VideoRepository implements IVideoRepository {

    private final VideoMapper m_videoMapper;


    @Override
    public List<RecommendedVideoDTO> findRecommendedVideoList() {
        return m_videoMapper.findRecommendedVideoList();
    }

    @Override
    public List<YoutubeVideoDTO> findPlayListDetail(Long youtubePlaylistId) {
        return  m_videoMapper.findPlayListDetail(youtubePlaylistId);
    }

    @Override
    public List<YoutubePlayListDTO> findPlayListByKeyword(String keyword, int offset, int count) {
        return m_videoMapper.findPlayListByKeyword(keyword, offset, count);
    }

    @Override
    public int selectTotalPlayListCountByKeyword(String keyword){
        return m_videoMapper.selectTotalPlayListCountByKeyword(keyword);
    }

    @Override
    public List<LectureNoteYoutubeVideoDTO> findPlayListDetailOfLectureNote(Long memberId, Long youtubePlaylistId, Long noteId) {
        return m_videoMapper.findPlayListDetailOfLectureNote(memberId, youtubePlaylistId, noteId);
    }

    @Override
    public long getDuration(Long lectureVideoId) {
        return Duration.parse(m_videoMapper.findDuration(lectureVideoId)).toSeconds();
    }

    @Override
    public long getProgress(Long lectureVideoId) {
        return m_videoMapper.findProgress(lectureVideoId);
    }

    @Override
    public Optional<Boolean> findVideoLikeStatus(Long id, Long videoId) {
        return m_videoMapper.findVideoLikeStatus(id, videoId);
    }

    @Override
    public void setLikeStatus(Long memberId, Long videoId, boolean likeStatus) {
        m_videoMapper.setLikeStatus(memberId, videoId, likeStatus);
    }

    @Override
    public void increaseLikeCount(Long videoId) {
        m_videoMapper.increaseLikeCount(videoId);
    }

    @Override
    public void updateLikeVideo(Long memberId, Long videoId, boolean likeStatus) {
        m_videoMapper.updateLikeVideo(memberId, videoId, likeStatus);
    }

    @Override
    public void decreaseLikeCount(Long videoId) {
        m_videoMapper.decreaseLikeCount(videoId);
    }

    @Override
    public void decreaseDislikeCount(Long videoId) {
        m_videoMapper.decreaseDislikeCount(videoId);
    }

    @Override
    public void increaseDislikeCount(Long videoId) {
        m_videoMapper.increaseDislikeCount(videoId);
    }

    @Override
    public Map<String, Long> selectLikeCountMap(Long videoId) {
        return m_videoMapper.selectLikeCountMap(videoId);
    }

    @Override
    public YoutubeVideoDTO findVideoDetail(Long videoId) {
        return m_videoMapper.findVideoDetail(videoId);
    }

    @Override
    public List<Long> findAllVideoIdByPlayList(Long playListId) {
        return m_videoMapper.findAllVideoIdByPlayList(playListId);
    }

    @Override
    public boolean isExistYoutubePlayList(Long playListId){
        return m_videoMapper.isExistYoutubePlayList(playListId);
    }

    @Override
    public boolean updateVideoPlaytime(Long id, Long playtime) {
        return m_videoMapper.updateVideoPlaytime(id, playtime) > 0;
    }

    @Override
    public void deleteLikeVideo(Long memberId, Long videoId) {
        m_videoMapper.deleteLikeVideo(memberId, videoId);
    }

    @Override
    public List<RecommendedPlayListDTO> findRecommendedPlayList() {
        return m_videoMapper.findRecommendedPlayList();
    }

    @Override
    public int selectTotalPlayListCount(int id, int idType) {
        return m_videoMapper.selectTotalPlayListCount(id, idType);
    }

    @Override
    public List<YoutubePlayListDTO> findPlayListById(int id, int idType, int pageNum, int playlistCount) {
        return m_videoMapper.findPlayListById(id, idType, pageNum, playlistCount);
    }
}
