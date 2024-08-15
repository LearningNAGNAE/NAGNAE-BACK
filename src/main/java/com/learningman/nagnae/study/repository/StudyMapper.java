package com.learningman.nagnae.study.repository;

import com.learningman.nagnae.domain.dto.StudyCategoryDto;
import com.learningman.nagnae.domain.dto.StudyImageDto;
import com.learningman.nagnae.domain.dto.StudyTextDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudyMapper {

    List<StudyCategoryDto> selectStudyCategoryList();

    List<StudyTextDto> selectStudyTextList(String categoryNo);

    List<StudyImageDto> selectStudyImageList(String categorySubNo);
}
