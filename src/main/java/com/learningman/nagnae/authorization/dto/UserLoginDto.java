package com.learningman.nagnae.authorization.dto;

import lombok.Data;

@Data
public class UserLoginDto {
	private int userno;
	private String username;
    private String email;
    private String password;
    private String nationlity;
    private String userhp;
}