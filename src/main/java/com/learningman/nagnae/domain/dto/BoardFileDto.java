package com.learningman.nagnae.domain.dto;
import lombok.Data;

@Data
public class BoardFileDto {
    private int boardNo;
    private int fileNo;
    private int insertUserNo;
    private int modifyUserNo;
}
