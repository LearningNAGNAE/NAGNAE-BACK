package com.learningman.nagnae.domain.response;

import com.learningman.nagnae.domain.dto.StudyCategoryDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TextStudyCategoryMsg extends ResponseMsg {

    private List<StudyCategoryDto> studyCategoryList;

    public TextStudyCategoryMsg(String code, String message, List<StudyCategoryDto> studyCategoryList) {
        super(code, message);
        this.studyCategoryList = studyCategoryList;
    }
}