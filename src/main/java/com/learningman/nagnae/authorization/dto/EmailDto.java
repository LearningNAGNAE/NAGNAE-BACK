package com.learningman.nagnae.authorization.dto;

import lombok.Data;

@Data
public class EmailDto {
	private String username;
	private String userhp;
    private String to;
    private String subject;
    private String text;
    private String userid;
}