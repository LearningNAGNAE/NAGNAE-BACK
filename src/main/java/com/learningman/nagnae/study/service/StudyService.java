package com.learningman.nagnae.study.service;

import com.learningman.nagnae.domain.dto.StudyCategoryDto;
import com.learningman.nagnae.domain.dto.StudyImageDto;
import com.learningman.nagnae.domain.dto.StudyTextDto;
import com.learningman.nagnae.study.repository.StudyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyMapper studyMapper;

    public List<StudyCategoryDto> selectStudyCategoryList() {
        return studyMapper.selectStudyCategoryList();
    }

    public List<StudyTextDto> selectStudyTextList(String categoryNo) {
        return studyMapper.selectStudyTextList(categoryNo);
    }

    public List<StudyImageDto> selectStudyImageList(String categorySubNo) {
        return studyMapper.selectStudyImageList(categorySubNo);
    }
}
