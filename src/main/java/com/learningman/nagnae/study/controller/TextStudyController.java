package com.learningman.nagnae.study.controller;

import com.learningman.nagnae.domain.dto.StudyCategoryDto;
import com.learningman.nagnae.study.service.TextStudyService;
import com.learningman.nagnae.util.JsonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/text-study")
@RequiredArgsConstructor
public class TextStudyController {

    private final TextStudyService textStudyService;

    @GetMapping("/category")
    public ResponseEntity<?> getTextStudyCategory() {
        List<StudyCategoryDto> studyCategoryList = textStudyService.selectStudyCategoryList();
        return ResponseEntity.ok(JsonResult.success(studyCategoryList));
    }
}
