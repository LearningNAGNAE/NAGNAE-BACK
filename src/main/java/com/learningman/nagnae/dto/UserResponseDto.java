package com.learningman.nagnae.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
	private int UserID;
    private String UserName;
    private String Email;


}