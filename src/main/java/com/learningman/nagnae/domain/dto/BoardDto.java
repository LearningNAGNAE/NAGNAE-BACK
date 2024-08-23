package com.learningman.nagnae.domain.dto;

import lombok.Data;

@Data
public class BoardDto {

	private long boardno;
	private int categoryno;
	private String title;
	private String content;
	private String summary;
	private int views;
	private int insertuserno;
	private String insertdate;
	private int modifyuserno;
	private String modifydate;
}
