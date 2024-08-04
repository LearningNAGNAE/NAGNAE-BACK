package com.learningman.nagnae.study.repository;

import com.learningman.nagnae.domain.dto.StudyCategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TextStudyMapper {

    List<StudyCategoryDto> selectStudyCategoryList();

}
