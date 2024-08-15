package com.learningman.nagnae.domain.response;

import com.learningman.nagnae.domain.dto.StudyTextDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudyTextMsg {

    private List<StudyTextDto> studyTextList;

}
