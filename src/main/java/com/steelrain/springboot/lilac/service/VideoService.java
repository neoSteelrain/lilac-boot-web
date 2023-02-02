package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.YoutubePlaylistDTO;
import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoService {

    private final VideoRepository m_videoRepository;


    public List<YoutubeVideoDTO> getRecommendedVideoList() {
        return m_videoRepository.findRecommendedVideoList();
    }

    public List<YoutubeVideoDTO> getPlayListDetail(Long youtubePlaylistId){
        return m_videoRepository.findPlayListDetail(youtubePlaylistId);
    }

    public List<YoutubePlaylistDTO> searchPlayList(String keyword) {
        return m_videoRepository.findPlayListByKeyword(keyword);
    }
}
