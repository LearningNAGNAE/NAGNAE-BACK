package com.learningman.nagnae.study.controller;

import com.learningman.nagnae.domain.dto.StudyCategoryDto;
import com.learningman.nagnae.domain.dto.StudyImageDto;
import com.learningman.nagnae.domain.dto.StudyTextDto;
import com.learningman.nagnae.domain.response.StudyCategoryMsg;
import com.learningman.nagnae.domain.response.StudyImageMsg;
import com.learningman.nagnae.domain.response.StudyTextMsg;
import com.learningman.nagnae.study.service.StudyService;
import com.learningman.nagnae.domain.response.ResponseMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/category")
    public ResponseEntity<ResponseMsg> getStudyCategory() {
        List<StudyCategoryDto> studyCategoryList = studyService.selectStudyCategoryList();
        return ResponseEntity.ok(ResponseMsg.success(new StudyCategoryMsg(studyCategoryList)));
    }

    @GetMapping("/text/{categoryNo}")
    public ResponseEntity<ResponseMsg> getStudyText(@PathVariable String categoryNo) {
        List<StudyTextDto> studyTextList = studyService.selectStudyTextList(categoryNo);
        return ResponseEntity.ok(ResponseMsg.success(new StudyTextMsg(studyTextList)));
    }

    @GetMapping("/image/{categorySubNo}")
    public ResponseEntity<ResponseMsg> getStudyImage(@PathVariable String categorySubNo) {
        List<StudyImageDto> studyImageList = studyService.selectStudyImageList(categorySubNo);
        return ResponseEntity.ok(ResponseMsg.success(new StudyImageMsg(studyImageList)));
    }
}
