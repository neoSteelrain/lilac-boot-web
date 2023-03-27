package com.steelrain.springboot.lilac.repository;

import com.steelrain.springboot.lilac.datamodel.LectureNoteModalDTO;
import com.steelrain.springboot.lilac.datamodel.LectureNoteDTO;
import com.steelrain.springboot.lilac.datamodel.PlayListVideoDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteDetailDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteDetailDTO.LectureNoteBook;
import com.steelrain.springboot.lilac.mapper.LectureNoteMapper;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LectureNoteRepository implements ILectureNoteRepository {

    private final LectureNoteMapper m_lectureNoteMapper;

    @Override
    public boolean saveDefaultLectureNote(LectureNoteDTO lectureNoteDTO){
        return m_lectureNoteMapper.saveDefaultLectureNote(lectureNoteDTO) > 0;
    }

    @Override
    public boolean saveLectureNote(LectureNoteDTO lectureNoteDTO){
        return m_lectureNoteMapper.saveLectureNote(lectureNoteDTO) > 0;
    }

    @Override
    public boolean deleteLectureNote(Long noteId){
        return m_lectureNoteMapper.deleteLectureNote(noteId) > 0;
    }

    @Override
    public boolean updateLectureNote(LectureNoteDTO lectureNoteDTO) {
        return m_lectureNoteMapper.updateLectureNote(lectureNoteDTO) > 0;
    }

    @Override
    public List<LectureNoteDTO> findNoteListByMember(Long memberId) {
        return m_lectureNoteMapper.findAllNoteListByMember(memberId);
    }

    @Override
    public boolean checkDuplicatedLectureNoteByMember(Long memberId, String title) {
        return m_lectureNoteMapper.findDuplicatedLectureNoteByMember(memberId, title) > 0;
    }

    @Override
    public boolean addVideoIdList(List<PlayListVideoDTO> videoIdList) {
        return m_lectureNoteMapper.addVideoIdList(videoIdList);
    }

    @Override
    public List<LectureNoteModalDTO> findLectureNoteListByPlayList(Long memberId) {
        return m_lectureNoteMapper.findLectureNoteListByPlayList(memberId);
    }

    @Override
    public LectureNoteDTO findLectureNoteByMember(Long memberId, Long noteId) {
        return m_lectureNoteMapper.findLectureNoteByMember(memberId, noteId);
    }

    @Override
    public List<LectureNoteDetailDTO.LectureVideoPlayListInfo> findVideoInfoByLectureNote(Long memberId, Long noteId) {
        List<LectureNoteDetailDTO.LectureVideoPlayListInfo> resultList = m_lectureNoteMapper.findVideoInfoByLectureNote(memberId, noteId);
        if(resultList.size() == 0){
            return resultList;
        }
        /*
            쿼리결과에는 채널의 id만 있고 타이틀은 가져오지 않고 있다. 가져오려면 join을 한번 더 하거나 스칼라서브쿼리로 가져오면 되지만
            join하지 않고 채널정보만 따로 쿼리를 날려서 repository 에서 채널정보를 설정해주는 방식으로 해보았다.
            - join 한번 더 하는것보다는 채널id로 바로 pk로 select 하는 것이 나중에 채널정보를 더 가져오는데 있어서 좀더 나은 생각이 들었다.
         */
        List<Long> channelIds = resultList.stream().map(LectureNoteDetailDTO.LectureVideoPlayListInfo::getChannelId).distinct().collect(Collectors.toList());
        List<ChannelTitleInfo> channelTitles = m_lectureNoteMapper.findChannelTitle(channelIds);
        for(LectureNoteDetailDTO.LectureVideoPlayListInfo info : resultList){
            Long chnId = info.getChannelId();
            for (ChannelTitleInfo channelTitle : channelTitles){
                if(Objects.equals(channelTitle.channelId, chnId)){
                    info.setChannelTitle(channelTitle.getChannelTitle());
                }
            }
        }
        return resultList;
    }

    /*
        유튜브 영상은 재생시간을 ISO 8601 duration 형식(P#DT#H#M#S)으로 표현하기 때문에
        자바에서 Duration 객체로 변환하여 사용한다.
     */
    @Override
    public Duration findTotalDurationOfPlayList(Long playListId) {
        List<String> durationList = m_lectureNoteMapper.findTotalDurationOfPlayList(playListId);
        long totalDurationValue = 0;
        for (String videoDuration : durationList) {
            totalDurationValue += Duration.parse(videoDuration).toSeconds();
        }
        return Duration.ofSeconds(totalDurationValue);
    }

    @Override
    public void deletePlayList(Long memberId, Long noteId, Long playListId) {
        m_lectureNoteMapper.deletePlayList(memberId, noteId, playListId);
    }

    @Override
    public void addBook(Long bookId, Long lectureNoteId, Long memberId) {
        m_lectureNoteMapper.addBook(bookId, lectureNoteId, memberId);
    }

    @Override
    public List<LectureNoteBook> findBookListByLectureNote(Long memberId, Long noteId) {
        return m_lectureNoteMapper.findBookListByLectureNote(memberId, noteId);
    }

    @Override
    public List<LectureNoteModalDTO> findLectureNoteListByBook(Long memberId) {
        return m_lectureNoteMapper.findLectureNoteListByBook(memberId);
    }

    @Override
    public void deleteBook(Long refId) {
        m_lectureNoteMapper.deleteBook(refId);
    }

    @Override
    public String[] findAllDuration(Long playlistId) {
        return m_lectureNoteMapper.findAllDuration(playlistId);
    }

    @Override
    public int findTotalProgress(Long memberId, Long noteId, Long playlistId) {
        return m_lectureNoteMapper.findTotalProgress(memberId, noteId, playlistId);
    }

    /**
     * 채널정보를 가져오는데 사용하는 DTO
     * LectureNoteRepository, LectureNoteMapper 에서만 사용하기 때문에 따로 dto 패키지에서 선언하지 않았다
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChannelTitleInfo{
        private Long channelId;
        private String channelTitle;
    }
}
