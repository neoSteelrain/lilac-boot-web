package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.YoutubeVideoDTO;
import com.steelrain.springboot.lilac.event.VideoListByPlayListEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureEventHandler {
    private final IVideoService m_videoService;


    @EventListener(VideoListByPlayListEvent.class)
    public void handleVideoListByPlayListEvent(VideoListByPlayListEvent event){
        List<Long> list = m_videoService.getAllVideoIdByPlayList(event.getPlayListId());
        event.setVideoDTOList(list);
    }
}
