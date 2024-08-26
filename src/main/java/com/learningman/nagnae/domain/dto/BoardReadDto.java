package com.learningman.nagnae.domain.dto;

import lombok.Data;

@Data
public class BoardReadDto {

	private String title;
	private String content;
    private String modifyDate;
    private int views;
    private String userName;
    private String nationlity;
    private String insertuserno;
    private String summary;
}
