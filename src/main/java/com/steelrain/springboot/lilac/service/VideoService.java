package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.config.PAGING_CONFIG;
import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoService implements IVideoService {

    private final VideoRepository m_videoRepository;


    @Override
    public List<RecommendedVideoDTO> getRecommendedVideoList() {
        return m_videoRepository.findRecommendedVideoList();
    }

    @Override
    public List<YoutubeVideoDTO> getPlayListDetail(Long youtubePlaylistId){
        return m_videoRepository.findPlayListDetail(youtubePlaylistId);
    }

    @Override
    public VideoPlayListSearchResultDTO searchPlayList(String keyword, int offset, int count) {
        return VideoPlayListSearchResultDTO.builder()
                                        .requestKeyword(keyword)
                                        .pageDTO(createPageDTO(keyword, offset, count))
                                        .playList(m_videoRepository.findPlayListByKeyword(keyword, offset-1, count))
                                        .build();
    }

    @Override
    public List<Long> getAllVideoIdByPlayList(Long playListId) {
        return m_videoRepository.findAllVideoIdByPlayList(playListId);
    }

    private PageDTO createPageDTO(String keyword, int offset, int playlistCount){
        int totalPlaylistCount = m_videoRepository.selectTotalPlayListCountByKeyword(keyword);
        int maxPage = (int)(Math.ceil( (double) totalPlaylistCount / playlistCount));
        int startPage = (((int)(Math.ceil((double) offset / PAGING_CONFIG.BLOCK_LIMIT))) - 1) * PAGING_CONFIG.BLOCK_LIMIT + 1;
        int endPage = startPage + PAGING_CONFIG.BLOCK_LIMIT -1;
        if(endPage > maxPage){
            endPage = maxPage;
        }
        return PageDTO.builder()
                .page(offset)
                .startPage(startPage)
                .endPage(endPage)
                .maxPage(maxPage).build();
    }

    public YoutubeVideoDTO getVideoDetail(Long videoId) {
        return m_videoRepository.findVideoDetail(videoId);
    }
}
