package com.learningman.nagnae.domain.dto;

import lombok.Data;

@Data
public class FileDto {
    private int userNo;
    private int fileNo;
    private int categoryNo;
    private String fileOriginName;
    private String fileSaveName;
    private String filePath;
    private int insertUserNo;
    private int modifyUserNo;
}