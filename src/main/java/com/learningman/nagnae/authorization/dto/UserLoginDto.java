package com.learningman.nagnae.authorization.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    private String Email;
    private String Password;
}