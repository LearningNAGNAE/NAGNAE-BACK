package com.learningman.nagnae.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
	
	private int userno;
    private String username;
    private String email;
    private String password;
    private String userhp;
    private String nationlity;


}