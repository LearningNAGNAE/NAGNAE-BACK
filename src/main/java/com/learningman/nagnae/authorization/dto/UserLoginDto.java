package com.learningman.nagnae.authorization.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    private String EMAIL;
    private String PASSWORD;
}