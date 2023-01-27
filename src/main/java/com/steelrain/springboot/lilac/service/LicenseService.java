package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.datamodel.LicenseDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseScheduleDTO;
import com.steelrain.springboot.lilac.datamodel.api.LicenseScheduleResponseDTO;
import com.steelrain.springboot.lilac.repository.ILicenseRespository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseService implements ILicenseService{

    private final ILicenseRespository m_licenseRespository;

    public LicenseService(ILicenseRespository licenseRespository){
        this.m_licenseRespository = licenseRespository;
    }

    @Override
    public LicenseDTO getLicenseSchedulesByCode(int licenseCode) {
        Optional<String> tmpName = m_licenseRespository.getLicenseName(licenseCode);
        if(tmpName.isEmpty()){
            return null;
        }

        LicenseScheduleResponseDTO responseDTO = m_licenseRespository.getLicenseSchedule(licenseCode);
        if(responseDTO == null){
            return null;
        }

        LicenseDTO licenseDTO = new LicenseDTO();
        licenseDTO.setLicenseName(tmpName.get());
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
                licenseDTO.setLicEndDate(schedule.getDocRegEndDt());
                continue;
            }
            if(Integer.parseInt(schedule.getDocExamStartDt()) <= now && Integer.parseInt(schedule.getDocExamEndDt()) >= now ){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 필기시험 진행중");
                licenseDTO.setLicEndDate(schedule.getDocExamEndDt());
                continue;
            }
            if(Integer.parseInt(schedule.getDocPassDt()) == now){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 필기시험 결과발표");
                licenseDTO.setLicEndDate(schedule.getDocPassDt());
                continue;
            }
            // 실기시험 단계 검사
            if(Integer.parseInt(schedule.getPracRegStartDt()) <= now && Integer.parseInt(schedule.getPracRegEndDt()) >= now ){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 실기시험 접수중");
                licenseDTO.setLicEndDate(schedule.getPracRegEndDt());
                continue;
            }
            if(Integer.parseInt(schedule.getPracExamStartDt()) <= now && Integer.parseInt(schedule.getPracExamEndDt()) >= now ){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 실기시험 진행중");
                licenseDTO.setLicEndDate(schedule.getPracExamEndDt());
                continue;
            }
            if(Integer.parseInt(schedule.getPracPassDt()) == now){
                licenseDTO.setLicStep(schedule.getImplSeq() + "회차 실기시험 결과발표");
                licenseDTO.setLicEndDate(schedule.getPracPassDt());
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

        // 대부분의 국가시험은 3회정도 치루므로, 리스트의 초기사이즈는 3으로 한다.
        List<LicenseScheduleDTO> resultList = new ArrayList<>(3);
        List<LicenseScheduleResponseDTO.LicenseSchedule> schedules = responseDTO.getBody().getScheduleList();
        /*for(int i=0, size=schedules.size() ; i <= size ; i++){
            LicenseScheduleDTO dto = new LicenseScheduleDTO();
            dto.setCategory(String.format("%s년 정기 %s %d회", schedules.get(i).getImplyy(), getLicenseCategoryName(schedules.get(i).getDescription()), schedules.get(i).getImplSeq()));
            resultList.add(dto);
        }*/
        for(LicenseScheduleResponseDTO.LicenseSchedule schedule : schedules){
            LicenseScheduleDTO dto = new LicenseScheduleDTO();
            String tmp = String.format("%s년 정기 %s %d회", schedule.getImplyy(), getLicenseCategoryName(schedule.getDescription()), schedule.getImplSeq());
            dto.setCategory(tmp);
            resultList.add(dto);
        }
        return resultList;
    }

    private String getLicenseCategoryName(String str){
        String[] tmp = StringUtils.split(str, " ");
        return tmp != null && tmp.length > 1 ? tmp[1] : " ";
    }
}