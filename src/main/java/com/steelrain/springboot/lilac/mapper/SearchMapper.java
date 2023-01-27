package com.steelrain.springboot.lilac.mapper;

import com.steelrain.springboot.lilac.datamodel.SubjectCodeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {

    @Select("SELECT name,key_word FROM tbl_subject WHERE is_active = 1 AND is_scheduled = 1")
    @Results(id="SubjectCodeMap", value={
            @Result(property = "name", column = "name"),
            @Result(property = "keyWord", column = "key_word")
    })
    List<SubjectCodeDTO> getSubjectCodes();
}
