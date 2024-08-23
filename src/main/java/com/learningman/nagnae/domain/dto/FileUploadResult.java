package com.learningman.nagnae.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadResult {
    private int fileNo;
    private String fileUrl;
}
