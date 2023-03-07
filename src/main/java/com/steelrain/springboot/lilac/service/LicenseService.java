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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        setCurrentStep(responseDTO, licenseDTO);
        licenseDTO.setScheduleList(parseLicenseScheduleJsonString(responseDTO));

        return licenseDTO;
    }

    // 시험일정의 현재 진행단계 알아내기
    private void setCurrentStep(LicenseScheduleResponseDTO responseDTO, final LicenseDTO licenseDTO){
        List<LicenseScheduleResponseDTO.LicenseSchedule> scheduleList = responseDTO.getBody().getScheduleList();
        if(scheduleList == null && scheduleList.size() == 0){
            return;
        }

        // 문자열 보다는 숫자로 하는 비교가 더 확실하므로 자격증날짜와 비교할 현재날짜를 int값으로 구한다.
        int now = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        //int now = 0102; 테스트용 날짜값
        licenseDTO.setLicenseDesc(String.format(scheduleList.get(0).getDescription()));
        for(LicenseScheduleResponseDTO.LicenseSchedule schedule : scheduleList){
            // 필기시험 단계 검사 0110, 0113, now 0112, 0116, 0119
            if(Integer.parseInt(schedule.getDocRegStartDt()) <= now && Integer.parseInt(schedule.getDocRegEndDt()) >= now ){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 필기시험 접수중");
                licenseDTO.setLicEndDate(convertDateFormat(schedule.getDocRegEndDt()));
                continue;
            }
            if(Integer.parseInt(schedule.getDocExamStartDt()) <= now && Integer.parseInt(schedule.getDocExamEndDt()) >= now ){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 필기시험 진행중");
                licenseDTO.setLicEndDate(convertDateFormat(schedule.getDocExamEndDt()));
                continue;
            }
            if(Integer.parseInt(schedule.getDocPassDt()) == now){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 필기시험 결과발표");
                licenseDTO.setLicEndDate(convertDateFormat(schedule.getDocPassDt()));
                continue;
            }
            // 실기시험 단계 검사
            if(Integer.parseInt(schedule.getPracRegStartDt()) <= now && Integer.parseInt(schedule.getPracRegEndDt()) >= now ){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 실기시험 접수중");
                licenseDTO.setLicEndDate(convertDateFormat(schedule.getPracRegEndDt()));
                continue;
            }
            if(Integer.parseInt(schedule.getPracExamStartDt()) <= now && Integer.parseInt(schedule.getPracExamEndDt()) >= now ){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 실기시험 진행중");
                licenseDTO.setLicEndDate(convertDateFormat(schedule.getPracExamEndDt()));
                continue;
            }
            if(Integer.parseInt(schedule.getPracPassDt()) == now){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 실기시험 결과발표");
                licenseDTO.setLicEndDate(convertDateFormat(schedule.getPracPassDt()));
                continue;
            }
            /*
            여기까지 오면 현재날짜가 시험일정에 해당하지않는 것이므로 해당사항 없음으로 설정
             */
            if(!StringUtils.hasText(licenseDTO.getLicStep())){
                licenseDTO.setLicStep("해당사항 없음");
                licenseDTO.setLicEndDate("-");
            }
        }
    }

    private String convertDateFormat(String src){
        Optional<String> result = StringFormatter.toDateFormattedString(src);
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
            // TODO :날짜 포매팅을 에너테이션으로 처리하는 방법을 찾아보자
            resultList.add(LicenseScheduleDTO.builder()
                            .category(String.format("%s년 정기 %s %d회", schedule.getImplyy(), getLicenseCategoryName(schedule.getDescription()), schedule.getImplSeq()))
                            .docRegPeriod(StringFormatter.toDateFormattedString(schedule.getDocRegStartDt()).get() + " - " + StringFormatter.toDateFormattedString(schedule.getDocRegEndDt()).get())
                            .docExam(StringFormatter.toDateFormattedString(schedule.getDocExamStartDt()) .get()+ " - " + StringFormatter.toDateFormattedString(schedule.getDocExamEndDt()).get())
                            .docPass(StringFormatter.toDateFormattedString(schedule.getDocPassDt()).get())
                            .pracReg(StringFormatter.toDateFormattedString(schedule.getPracRegStartDt()).get() + " - " + StringFormatter.toDateFormattedString(schedule.getPracRegEndDt()).get())
                            .pracExam(StringFormatter.toDateFormattedString(schedule.getPracExamStartDt()).get() + " - " + StringFormatter.toDateFormattedString(schedule.getPracExamEndDt()).get())
                            .pracPass(StringFormatter.toDateFormattedString(schedule.getPracPassDt()).get())
                            .build());
        }
        return resultList;
    }

    private String getLicenseCategoryName(String str){
        String[] tmp = StringUtils.split(str, " ");
        return tmp != null && tmp.length > 1 ? tmp[1] : " ";
    }
}
