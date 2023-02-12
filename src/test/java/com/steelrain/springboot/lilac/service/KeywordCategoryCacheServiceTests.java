package com.steelrain.springboot.lilac.service;

import com.steelrain.springboot.lilac.common.KeywordCategoryCacheService;
import com.steelrain.springboot.lilac.datamodel.LibraryRegionCodeDTO;
import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.util.List;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class KeywordCategoryCacheServiceTests {

    @Autowired
    private KeywordCategoryCacheService mgr;

    @Test
    @DisplayName("주제어코드 초기화 기능 테스트")
    public void testGetSubjectCodeList(){
        List<SubjectCodeDTO> res = mgr.getSubjectCodeList();

        assertThat(res != null);
    }

    @Test
    @DisplayName("지역코드 초기화 기능 테스트")
    public void testGetLibraryDetailRegionCodeList(){
        List<LibraryRegionCodeDTO> codes = mgr.getLibraryRegionCodeList();

        assertThat(codes != null);

        codes.stream().forEach(codeDTO -> {
            mgr.getLibraryDetailRegionCodeList(codeDTO.getCode()).stream().forEach(dtlCodeDTO ->{
                System.out.println("dtlCodeDTO : " + dtlCodeDTO.toString());
            });
        });
    }

    @Test
    @DisplayName("지역코드로 지역이름 얻어내기")
    public void testGetRegionName(){
        String name = mgr.getRegionName((short)11);
        assertThat(StringUtils.hasText(name));
        System.out.println(name);
    }
    
    @Test
    @DisplayName("세부지역코드로 세부지역이름 얻어내기")
    public void testGetDetailRegion(){
        String name = mgr.getDetailRegionName((short)11, 11220);
        assertThat(StringUtils.hasText(name));
        System.out.println(name);
    }
}
