package com.learningman.nagnae.domain.response;

import com.learningman.nagnae.domain.dto.StudyImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudyImageMsg {

    List<StudyImageDto> studyImageList;
}
