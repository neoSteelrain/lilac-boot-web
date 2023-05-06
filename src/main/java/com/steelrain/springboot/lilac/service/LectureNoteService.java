package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.ICacheService;
import com.steelrain.springboot.lilac.common.StringFormatter;
import com.steelrain.springboot.lilac.datamodel.*;
import com.steelrain.springboot.lilac.datamodel.view.BookAddModalDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteDetailDTO;
import com.steelrain.springboot.lilac.datamodel.view.LectureNoteStatus;
import com.steelrain.springboot.lilac.datamodel.view.PlayListAddModalDTO;
import com.steelrain.springboot.lilac.event.LicenseInfoByLectureNoteEvent;
import com.steelrain.springboot.lilac.event.MemberRegistrationEvent;
import com.steelrain.springboot.lilac.event.VideoListByPlayListEvent;
import com.steelrain.springboot.lilac.exception.LectureNoteException;
import com.steelrain.springboot.lilac.repository.ILectureNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 강의노트 서비스
 * - 강의노트의 비즈니스 로직을 구현
 * - 강의노트 관련된 이벤트를 처리/발행 한다
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LectureNoteService implements ILectureNoteService{

    private final ILectureNoteRepository m_lectureNoteRepository;
    private final ICacheService m_cacheService;
    private final ApplicationEventPublisher m_appEventPublisher;


    @Transactional
    @Override
    public void createDefaultLectureNote(Long memberId, String nickname){
        try{
            createLectureNote(memberId, String.format("%s 님의 기본강의노트", nickname), null, null);
        }catch(Exception ex){
            log.error("기본강의 노트 생성 예외 - createDefaultLectureNote() 예외 발생 - {}", ex);
            throw new LectureNoteException( String.format("기본 강의노트 생성 실패 - 회원 ID : %s", memberId), ex, memberId);
        }
    }

    @Transactional
    @Override
    public Long addLectureNote(Long memberId, String title, String description, Integer licenseId, Integer subjectId){
        Long noteId = null;
        try{
            if(checkDuplicatedLectureNoteByMember(memberId, title)){
                return null;
            }
            /*
             licenseId 는 자격증 id, subjectId 는 주제어 id 이다.
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

    @Transactional
    @Override
    public LectureNoteStatus removeLectureNote(Long noteId, Long memberId){
        try{
            int noteCnt = m_lectureNoteRepository.findLectureNoteCount(memberId);
            if(noteCnt == 1){
                return LectureNoteStatus.builder()
                        .isLast(true)
                        .isDeleted(false)
                        .build();
            }
            deleteLectureNote(noteId);
            return LectureNoteStatus.builder()
                    .isLast(false)
                    .isDeleted(true)
                    .build();
        }catch(Exception ex){
            throw new LectureNoteException( String.format("강의노트 삭제 실패 - 강의노트 ID : %s", noteId), ex, noteId);
        }
    }

    @Transactional
    @Override
    public void editLectureNote(LectureNoteDTO lectureNoteDTO) {
        try {
            updateLectureNote(lectureNoteDTO);
        } catch (Exception ex) {
            throw new LectureNoteException(String.format("강의노트 삭제 실패 - 강의노트 정보 : %s", lectureNoteDTO.toString()), ex, lectureNoteDTO);
        }
    }

    @Override
    public List<LectureNoteDTO> getLectureListByMember(Long memberId){
        return m_lectureNoteRepository.findNoteListByMember(memberId);
    }

    @Override
    @Transactional
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
            paramList.add(PlayListVideoDTO.builder()
                                        .lectureId(lectureNoteId)
                                        .lectureMemberId(memberId)
                                        .youtubeId(videoId)
                                        .playlistId(playListId)
                                        .build());
        }
        return  m_lectureNoteRepository.addVideoIdList(paramList);
    }

    /**
     * 회원이 선택한 재생목록을 강의노트에 추가하기 위해 회원의 강의노트목록을 반환하는 메서드
     * 회원이 가지고 있는 강의노트중에서 추가하려는 재생목록이 없는 강의노트만 모아서 보내준다
     * @param memberId 강의노트를 가져올 회원의 id
     * @param playListId 회원의 강의노트에 추가하려고 하는 재생목록의 id
     * @return playListId 로 지정된 재생목록을 포함하고 있지 않은 강의노트의 목록
     */
    @Override
    public List<PlayListAddModalDTO> getLectureNoteListByPlayListModal(Long memberId, Long playListId) {
        /*
            - 중복된 재생목록이 있으면 안된다
            - DB에서 값이 넘어올때 재생목록이 없는 노트는 재생목록값은 null 에서 -1로 치환되어서 넘어온다.
            - -1로 치환하는 이유 : 스트림으로 처리할때 NPE 가 발생하지 않도록 하기 위해서 null 대신 -1로 바꿔준다
            - 작업순서
            1. DB에서 회원의 모든 강의노트 및 강의노트에 있는 재생목록이 반환된다.
            2. 전달인자로 넘어온 재생목록이 회원이 가지고 있는 전체재생목록에 있는지 검사해서 추가하려는 재생목록이 이미 있는 강의노트id를 뽑아낸다
            3. 2번에서 뽑아낸 강의노트를 강의노트목록에서 빼고 강의노트목록을 반환
         */
        // 1. DB에서 회원의 모든 강의노트 및 강의노트에 있는 재생목록을 반환
        List<LectureNoteModalDTO> noteDTOList = m_lectureNoteRepository.findLectureNoteListByPlayList(memberId);
        // 2. 전달인자로 넘어온 재생목록이 회원이 가지고 있는 전체재생목록에 있는지 검사를 해서 추가하려는 재생목록이 이미 있는 강의노트id를 뽑아낸다
        List<Long> matchedNoteIdList = noteDTOList.stream().filter(note -> note.getPlayListId().equals(playListId))
                .map(LectureNoteModalDTO::getNoteId)
                .collect(Collectors.toList());

        // 3. 2번에서 뽑아낸 강의노트를 강의노트목록에서 빼고 강의노트목록을 반환
        Iterator<LectureNoteModalDTO> iter = noteDTOList.iterator();
        while(iter.hasNext()){
            LectureNoteModalDTO note = iter.next();
            for(Long id : matchedNoteIdList){
                if(note.getNoteId().equals(id)){
                    iter.remove();
                }
            }
        }
        List<PlayListAddModalDTO> resultList = new ArrayList<>(noteDTOList.size());
        for(LectureNoteModalDTO note : noteDTOList){
            resultList.add(PlayListAddModalDTO.builder()
                    .id(note.getNoteId())
                    .title(note.getNoteTitle())
                    .build());
        }
        return resultList;
        // 2. 전달인자로 넘어온 재생목록이 회원이 가지고 있는 전체재생목록에 있는지 검사를 해서 추가하려는 재생목록이 이미 있는 강의노트id를 뽑아낸다
        /*List<Long> matchedNoteIdList = noteDTOList.stream().filter(note -> note.getPlayListId().equals(playListId))
                .map(LectureNoteModalDTO::getNoteId)
                .collect(Collectors.toList());*/
        // 3. 2번에서 뽑아낸 강의노트를 강의노트목록에서 빼고 강의노트목록을 반환
        /*if(matchedNoteId.isPresent()){
            noteDTOList.stream().filter(note -> !note.getNoteId().equals(matchedNoteId.get())).distinct().forEach(note -> {
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
        return resultList;*/
    }

    /**
     * 회원이 선택한 도서르 강의노트에 추가하기 위해 회원의 강의노트목록을 반환하는 메서드
     * 회원이 가지고 있는 강의노트중에서 추가하려는 도서가 없는 강의노트만 모아서 보내준다
     * @param memberId 강의노트의 회원 ID
     * @param bookId 강의노트에 추가하려는 도서 ID
     * @return bookId 로 지정된 도서를 포함하고 있지 않은 강의노트목록
     */
    @Override
    public List<BookAddModalDTO> getLectureNoteListByBookModal(Long memberId, Long bookId) {
        /*
            - 중복된 재생목록이 있으면 안된다
            - getLectureNoteListByPlayListModal 메서드의 처리로직과 비슷하게 처리한다
            - DB에서 값이 넘어올때 재생목록이 없는 노트는 재생목록값은 null 에서 -1로 치환되어서 넘어온다
            - -1로 치환하는 이유 : 스트림으로 처리할때 NPE 가 발생하지 않도록 하기 위해서 null 대신 -1로 바꿔준다
            - 작업순서
            1. DB에서 회원의 모든 강의노트 및 강의노트에 있는 재생목록이 반환된다.
            2. 전달인자로 넘어온 도서Id를 가지고, 회원이 가지고 있는 전체재생목록에 있는지 검사해서 추가하려는 도서가 이미 있는 강의노트id를 뽑아낸다
            3. 2번에서 뽑아낸 강의노트를 강의노트목록에서 빼고 강의노트목록을 반환
         */
        List<LectureNoteModalDTO> noteDTOList = m_lectureNoteRepository.findLectureNoteListByBook(memberId);
        // 2. 전달인자로 넘어온 재생목록이 회원이 가지고 있는 전체재생목록에 있는지 검사를 해서 추가하려는 재생목록이 이미 있는 강의노트id를 뽑아낸다
        List<Long> matchedNoteIdList = noteDTOList.stream().filter(note -> note.getBookId().equals(bookId))
                .map(LectureNoteModalDTO::getNoteId)
                .collect(Collectors.toList());

        // 3. 2번에서 뽑아낸 강의노트를 강의노트목록에서 빼고 강의노트목록을 반환
        Iterator<LectureNoteModalDTO> iter = noteDTOList.iterator();
        while(iter.hasNext()){
            LectureNoteModalDTO note = iter.next();
            for(Long id : matchedNoteIdList){
                if(note.getNoteId().equals(id)){
                    iter.remove();
                }
            }
        }
        List<BookAddModalDTO> resultList = new ArrayList<>(noteDTOList.size());
        for(LectureNoteModalDTO note : noteDTOList){
            resultList.add(BookAddModalDTO.builder()
                    .id(note.getNoteId())
                    .title(note.getNoteTitle())
                    .build());
        }
        return resultList;
        /*List<LectureNoteModalDTO> noteDTOList = m_lectureNoteRepository.findLectureNoteListByBook(memberId);
        List<BookAddModalDTO> resultDTO = new ArrayList<>(noteDTOList.size());
        Optional<Long> matchedNoteId = noteDTOList.stream().filter(note -> note.getBookId().equals(bookId))
                .map(LectureNoteModalDTO::getNoteId)
                .findFirst();
        if(matchedNoteId.isPresent()){
            noteDTOList.stream().filter(note -> !note.getNoteId().equals(matchedNoteId.get())).distinct().forEach(note -> {
                resultDTO.add(BookAddModalDTO.builder()
                        .id(note.getNoteId())
                        .title(note.getNoteTitle())
                        .build());
            });
        }else{
            noteDTOList.stream().distinct().forEach(note -> {
                resultDTO.add(BookAddModalDTO.builder()
                        .id(note.getNoteId())
                        .title(note.getNoteTitle())
                        .build());
            });
        }
        return resultDTO;*/
    }

    @Override
    @Transactional
    public void removeBook(Long refId) {
        m_lectureNoteRepository.deleteBook(refId);
    }

    /**
     * 회원의 강의노트에 대한 정보를 화면에 출력하기 위한 정보를 반환하는 메서드
     * @param memberId 회원 ID
     * @param noteId 회원의 강의노트 ID
     * @return 회원의 강의노트의 화면출력정보
     */
    @Override
    public LectureNoteDetailDTO getLectureNoteDetailInfoByMember(Long memberId, Long noteId) {
        // 회원의 강의노트 기본정보 초기화
        LectureNoteDetailDTO noteDetailDTO = new LectureNoteDetailDTO();
        initLectureNoteDetailInfoByMember(memberId, noteId, noteDetailDTO);
        // 강의노트의 자격증정보 초기화
        initLicenseInfoByLectureNote(noteDetailDTO.getLicenseId(), noteDetailDTO.getSubjectId(), noteDetailDTO);
        // 강의노트에 등록된 도서목록 초기화
        initBookInfoByLectureNote(memberId, noteId, noteDetailDTO);
        // 강의노트에 등록된 재생목록 초기화
        initPlayListInfoByLectureNote(memberId, noteId, noteDetailDTO);
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

    @Override
    public void registerBook(Long bookId, Long lectureNoteId, Long memberId){
        m_lectureNoteRepository.addBook(bookId, lectureNoteId, memberId);
    }

    /**
     * MemberService에서 발행한 회원등록이벤트를 받아서 회원의 기본강의노트를 생성하는 핸들러
     * @param event 회원등록이벤트
     */
    @Async
    @EventListener(MemberRegistrationEvent.class)
    public void createDefaultLectureNoteByNewMember(MemberRegistrationEvent event){
        createDefaultLectureNote(event.getMemberId(), event.getMemberNickname());
    }

    public String getTotalDuration(Long playListId){
        return StringFormatter.toHM(extractTotalDuration(playListId));
    }

    // 강의노트에 등록된 도서정보목록을 LectureNoteDetailDTO에 설정한다.
    private void initBookInfoByLectureNote(Long memberId, Long noteId, final LectureNoteDetailDTO noteDetailDTO) {
        noteDetailDTO.setKakaoBookList( m_lectureNoteRepository.findBookListByLectureNote(memberId, noteId));
    }

    /*
        강의노트에 등록된 재생목록들을 LectureNoteDetailDTO에 설정한다.
        재생목록에 대한 수치정보들을 설정한다
        - 수치정보들
        1. 총 재생시간
        2. 학습시간
        3. 진도율
     */
    private LectureNoteDetailDTO initPlayListInfoByLectureNote(Long memberId, Long noteId, final LectureNoteDetailDTO noteDetailDTO){
        List<LectureNoteDetailDTO.LecturePlayListInfo> playlist = m_lectureNoteRepository.findPlayListInfoByLectureNote(memberId, noteId);
        int inProgress = 0;
        int completed = 0;
        for (LectureNoteDetailDTO.LecturePlayListInfo info : playlist) {
            // 총 재생시간 설정
            long tmpTotalDuration = extractTotalDuration(info.getPlayListId());
            info.setTotalDuration(tmpTotalDuration);
            info.setTotalDurationFormattedString(StringFormatter.toHM(tmpTotalDuration));
            // 학습시간 설정
            long tmpTotalPlaytime = extractTotalPlaytime(memberId,noteId,info.getPlayListId());
            info.setTotalPlaytime(tmpTotalPlaytime);
            info.setTotalPlaytimeFormattedString(StringFormatter.toHM(tmpTotalPlaytime));
            // 진도율 설정
            double progress = extractTotalProgress(tmpTotalDuration, tmpTotalPlaytime);
            info.setProgressStatus(progress);

            if(progress == 100.0){ // 완강 정보
                completed++;
            } else if (progress < 100.0) { // 학습중 정보
                inProgress++;
            }
        }
        noteDetailDTO.setInProgressLectureCount(inProgress);
        noteDetailDTO.setCompletedLectureCount(completed);
        noteDetailDTO.setVideoPlayList(playlist);
        return noteDetailDTO;
    }

    private long extractTotalDuration(Long playlistId){
        // 재생목록의 duration 합 구하기
        String[] durations = m_lectureNoteRepository.findAllDuration(playlistId);
        long result = 0L;
        for(String item : durations){
            result += Duration.parse(item).toSeconds();
        }
        return result;
    }

    private long extractTotalPlaytime(Long memberId, Long noteId, Long playlistId){
        return m_lectureNoteRepository.findTotalProgress(memberId, noteId, playlistId);
    }

    private double extractTotalProgress(long totalDuration, long totalPlaytime){
        return ((double)totalPlaytime / totalDuration) * 100;
    }

    /*
        회원의 자격증정보 초기화
        - licenseCode, subjectCode 2개 둘다 null인 경우는 기본강의노트이므로, 자격증정보를 얻어오지 않고 곧바로 리턴한다.
        - 강의노트 주제가 자격증이 아닌경우는 특별한 정보를 넘기지 않는다
     */
    private LectureNoteDetailDTO initLicenseInfoByLectureNote(Integer licenseId, Integer subjectId, final LectureNoteDetailDTO noteDetailDTO){
        if(Objects.isNull(licenseId) && Objects.isNull(subjectId)){
            return noteDetailDTO;
        }
        if(Objects.nonNull(licenseId)){
            // 자격증정보는 이벤트를 발행하여 LicenseService 에서 받아온다.
            LicenseInfoByLectureNoteEvent noteEvent = LicenseInfoByLectureNoteEvent.builder()
                    .licenseId(licenseId)
                    .build();
            m_appEventPublisher.publishEvent(noteEvent);
            LicenseDTO licDTO = noteEvent.getLicenseDTO();
            LectureNoteDetailDTO.LicenseInfo licInfo = createLicenseInfo(licDTO);
            noteDetailDTO.setNoteLicenseName(licInfo.getLicenseName());
            noteDetailDTO.setLicenseInfo(licInfo);
        }
        if(Objects.nonNull(subjectId)){
            noteDetailDTO.setNoteLicenseName(m_cacheService.getSubjectNameById(subjectId));
        }
        return noteDetailDTO;
    }

    private LectureNoteDetailDTO.LicenseInfo createLicenseInfo(LicenseDTO dto){
        if(dto.getScheduleList() == null && dto.getScheduleList().size() == 0){
            List<LicenseScheduleDTO> schInfoList = new ArrayList<>(1);
            LicenseScheduleDTO info = LicenseScheduleDTO.builder()
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
        List<LicenseScheduleDTO> schInfoList = new ArrayList<>(dto.getScheduleList().size());
        for(LicenseScheduleDTO sch : dto.getScheduleList()){
            LicenseScheduleDTO info = LicenseScheduleDTO.builder()
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
        return noteDetailDTO;
    }

    private LectureNoteDTO getLectureNoteInfoByMember(Long memberId, Long noteId){
        return m_lectureNoteRepository.findLectureNoteByMember(memberId, noteId);
    }

    private void updateLectureNote(LectureNoteDTO lectureNoteDTO){
        if(lectureNoteDTO.getLicenseId() <= -1){
            lectureNoteDTO.setLicenseId(null);
        }
        if(lectureNoteDTO.getSubjectId() <= -1){
            lectureNoteDTO.setSubjectId(null);
        }
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
