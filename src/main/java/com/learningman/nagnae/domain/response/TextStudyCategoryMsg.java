package com.learningman.nagnae.domain.response;

import com.learningman.nagnae.domain.dto.StudyCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TextStudyCategoryMsg {

    private List<StudyCategoryDto> studyCategoryList;

}