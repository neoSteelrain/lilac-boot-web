package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.StringFormatter;
import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseScheduleDTO;
import com.steelrain.springboot.lilac.datamodel.api.LicenseScheduleResponseDTO;
import com.steelrain.springboot.lilac.event.LicenseInfoByLectureNoteEvent;
import com.steelrain.springboot.lilac.event.LicenseSearchEvent;
import com.steelrain.springboot.lilac.repository.ILicenseRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 자격증 서비스
 * - 자격증의 비즈니스 로직을 구현
 * - 자격증 관련된 이벤트를 처리/발행한다
 */
@Service
@RequiredArgsConstructor
public class LicenseService implements ILicenseService{

    private final ILicenseRespository m_licenseRepository;


    @Override
    public LicenseDTO getLicenseSchedulesByCode(int licenseCode) {
        return getLicenseSchedule(licenseCode);
    }
    @Override
    public LicenseDTO getLicenseSchedulesById(int licenseId) {
        Optional<Integer> licenseCode = m_licenseRepository.getLicenseCodeById(licenseId);
        if(licenseCode.isEmpty()){
            return null;
        }
        return getLicenseSchedule(licenseCode.get());
    }

    @EventListener(LicenseSearchEvent.class)
    public void handleLicenseSearchEvent(LicenseSearchEvent event){
        event.setLicenseDTO(getLicenseSchedulesByCode(event.getCode()));
    }

    @EventListener(LicenseInfoByLectureNoteEvent.class)
    public void handleLicenseInfoByLectureNoteEvent(LicenseInfoByLectureNoteEvent event){
        event.setLicenseDTO(getLicenseSchedulesById(event.getLicenseId()));
    }


    private LicenseDTO getLicenseSchedule(int licenseCode){
        Optional<String> licenseName = m_licenseRepository.getLicenseNameByCode(licenseCode);
        if(licenseName.isEmpty()){
            return null;
        }

        LicenseScheduleResponseDTO responseDTO = m_licenseRepository.getLicenseSchedule(licenseCode);
        if(responseDTO == null){
            return null;
        }

        LicenseDTO licenseDTO = new LicenseDTO();
        licenseDTO.setLicenseCode(licenseCode);
        licenseDTO.setLicenseName(licenseName.get());
        initCurrentStep(responseDTO, licenseDTO);
        licenseDTO.setScheduleList(parseLicenseScheduleJsonString(responseDTO));

        return licenseDTO;
    }

    private void initCurrentStep(LicenseScheduleResponseDTO responseDTO, final LicenseDTO licenseDTO){
        List<LicenseScheduleResponseDTO.LicenseSchedule> scheduleList = responseDTO.getBody().getScheduleList();
        if(scheduleList == null || scheduleList.size() == 0){
            licenseDTO.setLicenseStepList(new ArrayList<>(0));
            return;
        }

        /*
            - 자격증스케쥴 리스트를 순회하면서 오늘날짜가 어느 진행단계에 속하는 지 알아낸다
            - 진행단계는 2개 이상일 수도 있다
         */
        int now = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Map<Integer, LicenseDTO.LicenseStep> stepMap = new HashMap<>(2);
        for(LicenseScheduleResponseDTO.LicenseSchedule schedule : scheduleList) {
            if(schedule.getImplSeq() <= 0){ // 공공데이터 API에서 회차가 0으로 가끔 넘어오는 경우가 있다
                continue;
            }
            if(StringUtils.hasText(schedule.getDocRegStartDt()) && StringUtils.hasText(schedule.getDocRegEndDt()) &&
                    Integer.parseInt(schedule.getDocRegStartDt()) <= now && Integer.parseInt(schedule.getDocRegEndDt()) >= now ){
                extractScheduleStep(licenseDTO, stepMap, schedule.getDocRegEndDt(), "회차 필기시험 접수중", schedule.getImplSeq(), schedule.getDescription());
                continue;
            }
            if(StringUtils.hasText(schedule.getDocExamStartDt()) && StringUtils.hasText(schedule.getDocExamEndDt()) &&
                    Integer.parseInt(schedule.getDocExamStartDt()) <= now && Integer.parseInt(schedule.getDocExamEndDt()) >= now ){
                extractScheduleStep(licenseDTO, stepMap, schedule.getDocExamEndDt(), "회차 필기시험 진행중", schedule.getImplSeq(), schedule.getDescription());
                continue;
            }
            if(StringUtils.hasText(schedule.getDocPassDt()) && Integer.parseInt(schedule.getDocPassDt()) == now){
                extractScheduleStep(licenseDTO, stepMap, schedule.getDocPassDt(), "회차 필기시험 결과발표", schedule.getImplSeq(), schedule.getDescription());
                continue;
            }
            if(StringUtils.hasText(schedule.getPracRegStartDt()) && StringUtils.hasText(schedule.getPracRegEndDt()) &&
                    Integer.parseInt(schedule.getPracRegStartDt()) <= now && Integer.parseInt(schedule.getPracRegEndDt()) >= now ){
                extractScheduleStep(licenseDTO, stepMap, schedule.getPracRegEndDt(), "회차 실기시험 접수중", schedule.getImplSeq(), schedule.getDescription());
                continue;
            }
            if(StringUtils.hasText(schedule.getPracExamStartDt()) && StringUtils.hasText(schedule.getPracExamEndDt()) &&
                    Integer.parseInt(schedule.getPracExamStartDt()) <= now && Integer.parseInt(schedule.getPracExamEndDt()) >= now ){
                extractScheduleStep(licenseDTO, stepMap, schedule.getPracExamEndDt(), "회차 실기시험 진행중", schedule.getImplSeq(), schedule.getDescription());
                continue;
            }
            if(StringUtils.hasText(schedule.getPracPassDt()) && Integer.parseInt(schedule.getPracPassDt()) == now){
                extractScheduleStep(licenseDTO, stepMap, schedule.getPracPassDt(), "회차 실기시험 결과발표", schedule.getImplSeq(), schedule.getDescription());
            }
        }
        if(stepMap.size() == 0){
            LicenseDTO.LicenseStep nonApplyStep = new LicenseDTO.LicenseStep();
            nonApplyStep.setLicStep("해당사항 없음");
            nonApplyStep.setLicEndDate("---");
            nonApplyStep.setLicenseDesc("---");
            // 0 은 스텝맵에 넣기 위한 더미값
            stepMap.put(Integer.valueOf(0), nonApplyStep);
        }
        licenseDTO.setLicenseStepList(new ArrayList<>(stepMap.values()));
    }

    /*
        자격증일정에 현재날짜가 해당된다면 실행되는 메서드
        회차에 해당하는 진행단계를 설정하여 현재진행단계 객체에 정보를 설정한다
     */
    private void extractScheduleStep(LicenseDTO licDTO, Map<Integer, LicenseDTO.LicenseStep> stepMap, String endDate, String stepState, int implSeq, String desc){
        LicenseDTO.LicenseStep step = null;
        Integer seq = Integer.valueOf(implSeq);
        if(stepMap.containsKey(seq)){
            step = stepMap.get(seq);
        }else{
            step = new LicenseDTO.LicenseStep();
            stepMap.put(implSeq, step);
        }
        step.setLicStep(implSeq + stepState);
        step.setLicEndDate(convertDateFormat(endDate));
        step.setLicenseDesc(desc);
    }

    private String convertDateFormat(String src){
        Optional<String> result = StringFormatter.toFormattedDateString(src);
        return result.isPresent() ? result.get() : src;
    }

    /*
    시험 회차별 일정 추출하기
    구분, 필기원서접수, 필기시험, 필기합격발표, 실기원서접수, 실기시험, 최총합격자발표일
    빈자리 접수기간은 시험 시행 초일 6일전 ~ 5일전 2일간.
     */
    private List<LicenseScheduleDTO> parseLicenseScheduleJsonString(LicenseScheduleResponseDTO responseDTO){
        if(responseDTO == null &&
                responseDTO.getBody() == null &&
                responseDTO.getBody().getScheduleList() == null &&
                responseDTO.getBody().getScheduleList().size() == 0){
            return new ArrayList<>(0);
        }

        List<LicenseScheduleDTO> resultList = new ArrayList<>(10);
        List<LicenseScheduleResponseDTO.LicenseSchedule> schedules = responseDTO.getBody().getScheduleList();

        for(LicenseScheduleResponseDTO.LicenseSchedule schedule : schedules){
            if(schedule.getImplSeq() <= 0){
                continue;
            }
            resultList.add(LicenseScheduleDTO.builder()
//                            .category(String.format("%s년 정기 %s", schedule.getImplyy(), getLicenseCategoryName(schedule.getDescription())))
                            .category(String.format("%s년 %d회", schedule.getImplyy(), schedule.getImplSeq()))
                            .docRegPeriod(StringUtils.hasText(schedule.getDocRegStartDt()) ? StringFormatter.toFormattedDateString(schedule.getDocRegStartDt()).get() + " - " + StringFormatter.toFormattedDateString(schedule.getDocRegEndDt()).get() : "미정")
                            .docExam(StringUtils.hasText(schedule.getDocExamStartDt()) ? StringFormatter.toFormattedDateString(schedule.getDocExamStartDt()) .get()+ " - " + StringFormatter.toFormattedDateString(schedule.getDocExamEndDt()).get() : "미정")
                            .docPass(StringUtils.hasText(schedule.getDocPassDt()) ? StringFormatter.toFormattedDateString(schedule.getDocPassDt()).get() : "미정")
                            .pracReg(StringUtils.hasText(schedule.getPracRegStartDt()) ? StringFormatter.toFormattedDateString(schedule.getPracRegStartDt()).get() + " - " + StringFormatter.toFormattedDateString(schedule.getPracRegEndDt()).get() : "미정")
                            .pracExam(StringUtils.hasText(schedule.getPracExamStartDt()) ? StringFormatter.toFormattedDateString(schedule.getPracExamStartDt()).get() + " - " + StringFormatter.toFormattedDateString(schedule.getPracExamEndDt()).get() : "미정")
                            .pracPass(StringUtils.hasText(schedule.getPracPassDt()) ? StringFormatter.toFormattedDateString(schedule.getPracPassDt()).get() : "미정")
                            .build());
        }
        return resultList;
    }

    private String getLicenseCategoryName(String str){
        String[] tmp = StringUtils.split(str, " ");
        return tmp != null && tmp.length > 1 ? tmp[1] : " ";
    }
}
