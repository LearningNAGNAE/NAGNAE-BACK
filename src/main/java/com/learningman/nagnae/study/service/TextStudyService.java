package com.learningman.nagnae.study.service;

import com.learningman.nagnae.domain.dto.StudyCategoryDto;
import com.learningman.nagnae.study.repository.TextStudyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TextStudyService {

    private final TextStudyMapper TextStudyMapper;

    public List<StudyCategoryDto> selectStudyCategoryList() {
        return TextStudyMapper.selectStudyCategoryList();
    }
}
