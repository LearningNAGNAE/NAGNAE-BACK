package com.learningman.nagnae.domain.dto;

import lombok.Data;

@Data
public class BoardListDto {
	
	private int boardno;
	private String title;
    private String insertDate;
    private int views;
    private String userName;

}
