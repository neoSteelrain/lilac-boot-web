package com.steelrain.springboot.lilac.common;

import com.steelrain.springboot.lilac.datamodel.LibraryDetailRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LibraryRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.LicenseCodeDTO;
import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;
import com.steelrain.springboot.lilac.mapper.SearchMapper;
import com.steelrain.springboot.lilac.service.ICacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class KeywordCategoryCacheService implements ICacheService {

    private static List<SubjectCodeDTO> m_subjectCodeList = null;
    private static List<LibraryRegionCodeDTO> m_libRegionCodeList = null;
    private static List<LicenseCodeDTO> m_licenseCodeList = null;
    private static Map<Short, Object> m_libDetailRegionCodeMap = null;
    private final SearchMapper m_searchMapper;

    public KeywordCategoryCacheService(SearchMapper sm){
        this.m_searchMapper = sm;

        initKeywordMap();
    }

    @Override
    public List<SubjectCodeDTO> getSubjectCodeList(){
        if(m_subjectCodeList == null){
            return new ArrayList<>(0);
        }
        return m_subjectCodeList;
    }

    @Override
    public List<LibraryRegionCodeDTO> getLibraryRegionCodeList(){
        if(m_libRegionCodeList == null){
            return new ArrayList<>(0);
        }
        return m_libRegionCodeList;
    }

    @Override
    public List<LibraryDetailRegionCodeDTO> getLibraryDetailRegionCodeList(short regionCode){
        if(m_libDetailRegionCodeMap == null){
            return new ArrayList<>(0);
        }
        return m_libDetailRegionCodeMap.containsKey(regionCode) ?
                (List<LibraryDetailRegionCodeDTO>) m_libDetailRegionCodeMap.get(regionCode) : new ArrayList<>(0);
    }

    @Override
    public List<LicenseCodeDTO> getLicenseCodeList(){
        if(m_licenseCodeList == null){
            return new ArrayList<>(0);
        }
        return m_licenseCodeList;
    }

    @Override
    public String getLicenseKeyword(int licenseCode) {
        return m_licenseCodeList.stream()
                .filter(license -> license.getCode().intValue() == licenseCode)
                .map(LicenseCodeDTO::getKeyWord)
                .findFirst()
                .get();
    }

    @Override
    public String getRegionName(short region) {
        return m_libRegionCodeList.stream()
                .filter(regionDTO -> (regionDTO.getCode() == region))
                .map(LibraryRegionCodeDTO::getName)
                .findFirst()
                .get();
    }

    @Override
    public String getDetailRegionName(short region, int detailRegion) {
        return ((List<LibraryDetailRegionCodeDTO>) m_libDetailRegionCodeMap.get(region)).stream()
                .filter(detailRegionDTO -> (detailRegionDTO.getCode() == detailRegion))
                .map(LibraryDetailRegionCodeDTO::getDetailName)
                .collect(Collectors.joining());
    }

    @Override
    public String getSubjectKeyword(int subjectCode) {
        return m_subjectCodeList.stream()
                .filter(subject -> subject.getCode() == subjectCode)
                .findFirst()
                .get().getKeyWord();
    }

    @Override
    public String getSubjectName(int subjectCode) {
        return m_subjectCodeList.stream()
                .filter(subject -> subject.getCode() == subjectCode)
                .findFirst()
                .get().getName();
    }

    @Override
    public String getSubjectKeywordBook(int subjectCode) {
        return m_subjectCodeList.stream()
                .filter(subject -> subject.getCode() == subjectCode)
                .findFirst()
                .get().getKeyWordBook();
    }

    private void initKeywordMap(){
        // 주제분류 초기화
        m_subjectCodeList = m_searchMapper.getSubjectCodes();
        // 지역코드 초기화
        m_libRegionCodeList = m_searchMapper.getLibRegionCodes();
        // 자격증분류 초기화
        m_licenseCodeList = m_searchMapper.getLicenseCodes();
        // 세부지역코드 초기화
        List<Short> codes = m_libRegionCodeList.stream()
                .map(LibraryRegionCodeDTO::getCode)
                .sorted()
                .collect(Collectors.toList());
        m_libDetailRegionCodeMap = new HashMap<>(codes.size());

        List<LibraryDetailRegionCodeDTO> allDtlRegionCodeList = m_searchMapper.getAllLibDetailRegionCodes();
        for(Short code : codes){
            List<LibraryDetailRegionCodeDTO> tmp = new ArrayList<>(20);
            for(LibraryDetailRegionCodeDTO dto : allDtlRegionCodeList){
                if(code.shortValue() == dto.getRegionCode().shortValue()){
                    tmp.add(dto);
                }
            }
            m_libDetailRegionCodeMap.put(code, tmp);
        }
    }
}
