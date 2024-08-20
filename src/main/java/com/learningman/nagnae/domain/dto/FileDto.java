package com.learningman.nagnae.domain.dto;

import lombok.Data;

@Data
public class FileDto {
    private int fileNo;
    private int categoryNo;
    private String fileOriginName;
    private String fileSaveName;
    private String filePath;
    // INSERT_DATE와 MODIFY_DATE는 데이터베이스에서 자동으로 설정되므로 여기서는 생략
}