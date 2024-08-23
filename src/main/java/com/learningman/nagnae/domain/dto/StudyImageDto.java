package com.learningman.nagnae.domain.dto;

import lombok.Data;

@Data
public class StudyImageDto {

    private int fileNo;
    private String fileOriginName;
    private String fileSaveName;
    private String filePath;
    private String description;

}
