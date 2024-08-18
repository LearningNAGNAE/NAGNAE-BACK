package com.learningman.nagnae.domain.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatHistoryDto {
    private Integer chatHisNo;
    private Integer chatHisSeq;
    private Integer categoryNo;
    private String question;
    private String answer;
    private Integer userNo;
    private LocalDateTime insertDate;
}