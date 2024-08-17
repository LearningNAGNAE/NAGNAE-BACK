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
    private int fileno;
	private int categoryno;
    private String savename;
    private String orgname;
    private String filepath;
    private int insertuserno;
    private String insertdate;
    private int modifyuserno;
    private String modifydate;


}