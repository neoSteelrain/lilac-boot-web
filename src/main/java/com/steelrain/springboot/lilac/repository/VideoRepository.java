package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.YoutubePlaylistDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class VideoRepository {

    private final VideoMapper m_videoMapper;


    public List<YoutubeVideoDTO> findRecommendedVideoList() {
        return m_videoMapper.findRecommendedVideoList();
    }

    public List<YoutubeVideoDTO> findPlayListDetail(Long youtubePlaylistId) {
        return  m_videoMapper.findPlayListDetail(youtubePlaylistId);
    }

    public List<YoutubePlaylistDTO> findPlayListByKeyword(String keyword) {
        return m_videoMapper.findPlayListByKeyword(keyword);
    }
}
