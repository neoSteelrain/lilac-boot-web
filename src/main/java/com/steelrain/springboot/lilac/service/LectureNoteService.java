package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteDetailDTO;
import com.steelrain.springboot.lilac.datamodel.view.PlayListAddModalDTO;
import com.steelrain.springboot.lilac.event.LicenseInfoByLectureNoteEvent;
import com.steelrain.springboot.lilac.event.VideoListByPlayListEvent;
import com.steelrain.springboot.lilac.exception.LectureNoteException;
import com.steelrain.springboot.lilac.repository.LectureNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureNoteService implements ILectureNoteService{

    private final LectureNoteRepository m_lectureNoteRepository;
    private final ApplicationEventPublisher m_appEventPublisher;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createDefaultLectureNote(Long memberId, String nickname){
        try{
            createLectureNote(memberId, String.format("%s 님의 기본강의노트", nickname), null, null);
        }catch(Exception ex){
            log.error("기본강의 노트 생성 예외 - createDefaultLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException( String.format("기본 강의노트 생성 실패 - 회원 ID : %s", memberId), ex, memberId);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addLectureNote(Long memberId, String title, String description, Integer licenseId, Integer subjectId){
        Long noteId = null;
        try{
            if(checkDuplicatedLectureNoteByMember(memberId, title)){
                return null;
            }
            /*
             licenseId 는 자격증 id, subjectId 는 키워드 id 이다.
             licenseId, subjectId 는 XOR 관계 이기 떄문에 하나만 선택해야 한다.
             */
            noteId = createLectureNote(memberId, title, description, licenseId, subjectId);
        }catch(Exception ex){
            log.error("강의 노트 생성 예외 - addLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException( String.format("강의노트 생성 실패 - 회원 ID : %s", memberId), ex, memberId);
        }
        return noteId;
    }

    private boolean checkDuplicatedLectureNoteByMember(Long memberId, String title){
        return m_lectureNoteRepository.checkDuplicatedLectureNoteByMember(memberId, title);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeLectureNote(Long noteId){
        try{
            deleteLectureNote(noteId);
        }catch(Exception ex){
            log.error("강의노트 삭제 예외 - removeLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException( String.format("강의노트 삭제 실패 - 강의노트 ID : %s", noteId), ex, noteId);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editLectureNote(LectureNoteDTO lectureNoteDTO) {
        try {
            updateLectureNote(lectureNoteDTO);
        } catch (Exception ex) {
            log.error("강의노트 수정 예외 - modifyLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException(String.format("강의노트 삭제 실패 - 강의노트 정보 : %s", lectureNoteDTO.toString()), ex, lectureNoteDTO);
        }
    }

    @Override
    public List<LectureNoteDTO> getLectureListByMember(Long memberId){
        return m_lectureNoteRepository.findNoteListByMember(memberId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addYoutubePlayListToLectureNote(Long lectureNoteId, Long playListId, Long memberId) {
        VideoListByPlayListEvent event = VideoListByPlayListEvent.builder()
                                                                 .playListId(playListId)
                                                                 .memberId(memberId)
                                                                 .lectureNoteId(lectureNoteId)
                                                                 .build();
        m_appEventPublisher.publishEvent(event);
        /*
        유튜브영상 id 목록가져오기
        ref_tbl_lecture_youtube 테이블에 insert 하기
        insert 성공여부 반환
         */
        List<Long> videoIdList = event.getVideoDTOList();
        List<PlayListVideoDTO> paramList = new ArrayList<>(videoIdList.size());
        for(Long videoId : videoIdList){
            PlayListVideoDTO dto = new PlayListVideoDTO();
            dto.setLectureId(lectureNoteId);
            dto.setLectureMemberId(memberId);
            dto.setYoutubeId(videoId);
            paramList.add(dto);
        }
        return  m_lectureNoteRepository.addVideoIdList(paramList);
    }

    /**
     * 회원이 선택한 재생목록을 강의노트에 추가하기 위해 회원의 강의노트목록을 반환하는 서비스
     * 회원이 가지고 있는 강의노트중에서 추가하려는 재생목록이 없는 강의노트만 모아서 보내줘야 한다
     * 중복된 재생목록이 있으면 안된다.
     * @param memberId 강의노트를 가져올 회원의 id
     * @param playListId 회원의 강의노트에 추가하려고 하는 재생목록의 id
     * @return playListId 로 지정된 재생목록을 포함하고 있지 않은 강의노트의 목록
     */
    @Override
    public List<PlayListAddModalDTO> getLectureNoteListByMemberModal(Long memberId, Long playListId) {
        /*
            DB에서 값이 넘어올때 재생목록이 없는 노트는 재생목록값은 null 에서 -1로 치환되어서 넘어온다.
            - 작업순서
            1. DB에서 회원의 모든 강의노트 및 강의노트에 있는 재생목록이 반환된다.
            2. 전달인자로 넘어온 재생목록이 회원이 가지고 있는 전체재생목록에 있는지 검사를 해서 추가하려는 재생목록이 이미 있는 강의노트id를 뽑아낸다
            3. 2번에서 뽑아낸 강의노트를 강의노트목록에서 빼고 강의노트목록을 반환
         */
        List<LectureNoteModalDTO> noteDTOList = m_lectureNoteRepository.findLectureNoteListByMember(memberId);
        List<PlayListAddModalDTO> resultList = new ArrayList<>(noteDTOList.size());
        Optional<Long> matchedNoteNo = noteDTOList.stream().filter(note -> note.getPlayListId().equals(playListId))
                                                 .map(LectureNoteModalDTO::getNoteId)
                                                 .findFirst();
        if(matchedNoteNo.isPresent()){
            noteDTOList.stream().filter(note -> !note.getNoteId().equals(matchedNoteNo.get())).distinct().forEach(note -> {
                resultList.add(PlayListAddModalDTO.builder()
                        .id(note.getNoteId())
                        .title(note.getNoteTitle())
                        .build());
            });
        }else{
         noteDTOList.stream().distinct().forEach(note -> {
             resultList.add(PlayListAddModalDTO.builder()
                     .id(note.getNoteId())
                     .title(note.getNoteTitle())
                     .build());
         });
        }
        return resultList;
    }

    /**
     * 회원의 강의노트에 대한 정보를 화면에 출력하기 위한 정보를 반환하는 서비스
     * @param memberId 회원 ID
     * @param noteId 회원의 강의노트 ID
     * @return 회원의 강의노트의 화면출력정보
     */
    @Override
    public LectureNoteDetailDTO getLectureNoteDetailInfoByMember(Long memberId, Long noteId) {
        // 회원의 강의노트 기본정보 초기화
        LectureNoteDetailDTO noteDetailDTO = new LectureNoteDetailDTO();
        noteDetailDTO = initLectureNoteDetailInfoByMember(memberId, noteId, noteDetailDTO);
        // 강의노트의 자격증정보 초기화
        initLicenseInfoByLectureNote(noteDetailDTO.getLicenseId(), noteDetailDTO.getSubjectId(), noteDetailDTO);
        // 강의노트의 등록된 재생목록 초기화
        initVideoInfoByLectureNote(memberId, noteId, noteDetailDTO);
        return noteDetailDTO;
    }

    @Override
    public void removePlayList(Long memberId, Long noteId, Long playListId) {
        m_lectureNoteRepository.deletePlayList(memberId, noteId, playListId);
    }

    @Override
    public LectureNoteDTO getLectureNoteByMember(Long memberId, Long noteId) {
        return m_lectureNoteRepository.findLectureNoteByMember(memberId, noteId);
    }

    /*
        강의노트에 등록된 재생목록들에 대한 정보를 초기화
     */
    private LectureNoteDetailDTO initVideoInfoByLectureNote(Long memberId, Long noteId, final LectureNoteDetailDTO noteDetailDTO){
        List<LectureNoteDetailDTO.LectureVideoPlayListInfo> playlist = m_lectureNoteRepository.findVideoInfoByLectureNote(memberId, noteId);
        // 재생목록의 진행상황을 설정해야 하지만 아직 유튜브 플레이어에서 재생된 시간을 가져오는 방법을 모르기 때문에 일단 재생목록의 전체 재생시간으로 대체한다
        for (LectureNoteDetailDTO.LectureVideoPlayListInfo info : playlist) {
            Duration totalDuration = m_lectureNoteRepository.findTotalDurationOfPlayList(info.getPlayListId());
            info.setTotalDuration(totalDuration.toSeconds()); // 유튜브는 초단위 까지만 사용하기 때문에 전체재생시간은 초단위로 설정
            info.setTotalDurationFormattedString(String.format("%d시간:%d분:%d초", totalDuration.toHoursPart(),totalDuration.toMinutesPart(), totalDuration.toSecondsPart()));
        }
        noteDetailDTO.setVideoPlayList(playlist);
        return noteDetailDTO;
    }

    /*
        회원의 자격증정보 초기화
        - licenseCode, subjectCode 2개 둘다 null인 경우는 기본강의노트이므로, 자격증정보를 얻어오지 않고 곧바로 리턴한다.
        - 강의노트 주제가 자격증이 아닌경우는 아직 미구현이다. TODO : 강의노트 주제가 자격증이 아닌 키워드인 경우를 구현해야 한다.
     */
    private LectureNoteDetailDTO initLicenseInfoByLectureNote(Integer licenseId, Integer subjectId, final LectureNoteDetailDTO noteDetailDTO){
        if(Objects.isNull(licenseId) && Objects.isNull(subjectId)){
            return noteDetailDTO;
        }
        // 자격증정보는 이벤트를 발행하여 LicenseService 에서 받아온다.
        LicenseInfoByLectureNoteEvent noteEvent = LicenseInfoByLectureNoteEvent.builder()
                .licenseId(licenseId)
                .build();
        m_appEventPublisher.publishEvent(noteEvent);
        LicenseDTO licDTO = noteEvent.getLicenseDTO();
        LectureNoteDetailDTO.LicenseInfo licInfo = createLicenseInfo(licDTO);
        noteDetailDTO.setNoteLicenseName(licInfo.getLicenseName());
        noteDetailDTO.setLicenseInfo(licInfo);
        return noteDetailDTO;
    }

    private LectureNoteDetailDTO.LicenseInfo createLicenseInfo(LicenseDTO dto){
        if(dto.getScheduleList() == null && dto.getScheduleList().size() == 0){
            List<LectureNoteDetailDTO.LicenseScheduleInfo> schInfoList = new ArrayList<>(1);
            LectureNoteDetailDTO.LicenseScheduleInfo info = LectureNoteDetailDTO.LicenseScheduleInfo.builder()
                    .category("-")
                    .docRegPeriod("-")
                    .docExam("-")
                    .docPass("-")
                    .pracReg("-")
                    .pracExam("-")
                    .pracPass("-")
                    .build();
            schInfoList.add(info);
            return LectureNoteDetailDTO.LicenseInfo.builder()
                    .licenseCode(-1)
                    .licenseName("-")
                    .licenseDesc("-")
                    .licStep("-")
                    .licEndDate("-")
                    .licenseScheduleList(schInfoList)
                    .build();
        }
        List<LectureNoteDetailDTO.LicenseScheduleInfo> schInfoList = new ArrayList<>(dto.getScheduleList().size());
        for(LicenseScheduleDTO sch : dto.getScheduleList()){
            LectureNoteDetailDTO.LicenseScheduleInfo info = LectureNoteDetailDTO.LicenseScheduleInfo.builder()
                    .category(sch.getCategory())
                    .docRegPeriod(sch.getDocRegPeriod())
                    .docExam(sch.getDocExam())
                    .docPass(sch.getDocPass())
                    .pracReg(sch.getPracReg())
                    .pracExam(sch.getPracExam())
                    .pracPass(sch.getPracPass())
                    .build();
            schInfoList.add(info);
        }
        return LectureNoteDetailDTO.LicenseInfo.builder()
                .licenseCode(dto.getLicenseCode())
                .licenseName(dto.getLicenseName())
                .licenseDesc(dto.getLicenseDesc())
                .licStep(dto.getLicStep())
                .licEndDate(dto.getLicEndDate())
                .licenseScheduleList(schInfoList)
                .build();
    }


    // 회원의 강의노트 기본정보 초기화
    private LectureNoteDetailDTO initLectureNoteDetailInfoByMember(Long memberId, Long noteId, final LectureNoteDetailDTO noteDetailDTO){
        // 회원의 강의노트 정보설정 시작
        LectureNoteDTO noteDTO = this.getLectureNoteInfoByMember(memberId, noteId);
        noteDetailDTO.setNoteId(noteId);
        noteDetailDTO.setNoteTitle(noteDTO.getTitle());
        noteDetailDTO.setNoteDescription(noteDTO.getDescription());
        // licenseId, subjectId가 들다 null 이면 아직 주제설정이 안된 기본노트, licenseId, subjectId 는 둘중에 하나는 null이다.
        noteDetailDTO.setLicenseId(noteDTO.getLicenseId());
        noteDetailDTO.setSubjectId(noteDTO.getSubjectId());

        List<LectureNoteDTO> noteDTOList = m_lectureNoteRepository.findNoteListByMember(memberId);
        int totalNoteCnt = noteDTOList.size();
        noteDetailDTO.setTotalNoteCount(totalNoteCnt); // 회원의 모든강의노트개수
        noteDetailDTO.setInProgressNoteCount(-1); // 회원의 진행중인 강의노트개수 임시값 -1
        noteDetailDTO.setCompletedNoteCount(-1); // 회원의 완료된 강의노트개수 임시값 -1

        return noteDetailDTO;
    }

    private LectureNoteDTO getLectureNoteInfoByMember(Long memberId, Long noteId){
        return m_lectureNoteRepository.findLectureNoteByMember(memberId, noteId);
    }

    private void updateLectureNote(LectureNoteDTO lectureNoteDTO){
        m_lectureNoteRepository.updateLectureNote(lectureNoteDTO);
    }

    private void deleteLectureNote(Long noteId){
        m_lectureNoteRepository.deleteLectureNote(noteId);
    }


    private Long createLectureNote(Long memberId, String title, Integer licenseId, Integer subjectId){
        LectureNoteDTO lectureNoteDTO = LectureNoteDTO.builder()
                .memberId(memberId)
                .title(title)
                .licenseId(licenseId)
                .subjectId(subjectId)
                .build();
        m_lectureNoteRepository.saveDefaultLectureNote(lectureNoteDTO);
        return lectureNoteDTO.getId();
    }

    private Long createLectureNote(Long memberId, String title, String description, Integer licenseId, Integer subjectId){
        LectureNoteDTO lectureNoteDTO = LectureNoteDTO.builder()
                .memberId(memberId)
                .title(title)
                .description(description)
                .licenseId(licenseId)
                .subjectId(subjectId)
                .build();
        m_lectureNoteRepository.saveLectureNote(lectureNoteDTO);
        return lectureNoteDTO.getId();
    }
}
