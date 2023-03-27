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

    public int selectTotalPlayListCountByKeyword(String keyword){
        return m_videoMapper.selectTotalPlayListCountByKeyword(keyword);
    }

    @Override
    public List<LectureNoteYoutubeVideoDTO> findPlayListDetailOfLectureNote(Long memberId, Long youtubePlaylistId) {
        return m_videoMapper.findPlayListDetailOfLectureNote(memberId, youtubePlaylistId);
    }

    @Override
    public long getDuration(Long lectureVideoId) {
        return Duration.parse(m_videoMapper.findDuration(lectureVideoId)).toSeconds();
    }

    @Override
    public long getProgress(Long lectureVideoId) {
        return m_videoMapper.findProgress(lectureVideoId);
    }

    public YoutubeVideoDTO findVideoDetail(Long videoId) {
        return m_videoMapper.findVideoDetail(videoId);
    }

    public List<Long> findAllVideoIdByPlayList(Long playListId) {
        return m_videoMapper.findAllVideoIdByPlayList(playListId);
    }

    public boolean isExistYoutubePlayList(Long playListId){
        return m_videoMapper.isExistYoutubePlayList(playListId);
    }

    @Override
    public boolean updateVideoPlaytime(Long id, Long playtime) {
        return m_videoMapper.updateVideoPlaytime(id, playtime) > 0;
    }

    public List<RecommendedPlayListDTO> findRecommendedPlayList() {
        return m_videoMapper.findRecommendedPlayList();
    }
}
