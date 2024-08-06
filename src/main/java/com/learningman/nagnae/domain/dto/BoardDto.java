package com.learningman.nagnae.domain.dto;

import lombok.Data;

@Data
public class BoardDto {

	private int postid;
	private int userid;
	private int boardid;
	private String title;
	private String content;
	private String createdate;
	private String lastmodifieddata;
	private int hits;
}
