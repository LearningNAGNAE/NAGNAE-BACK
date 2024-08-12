package com.learningman.nagnae.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
	private int USER_NO;
    private String USER_NAME;
    private String EMAIL;
    private String PASSWORD;


}