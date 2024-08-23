package com.learningman.nagnae.domain.dto;
import lombok.Data;

@Data
public class BoardFileDto {
    private long boardNo;
    private int fileNo;
    private int insertUserNo;
    private int modifyUserNo;
}
