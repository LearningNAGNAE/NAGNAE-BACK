package com.learningman.nagnae.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class GoogleAuthResponseDto {
	private String token;
    private boolean NewUser;
    private String email;
    private String name;
    private int userno;
    private String username;
    private String password;
    private String nationlity;
    private boolean activeyn;
    private boolean withdrawyn;
    private boolean anonymizeyn;
    private int insertuserno;
    private String insertdate;
    private int modifyuserno;
    private String modifydate;
    private String userhp;
    private int fileno;
    private int categoryno;
    private String savename;
    private String orgname;
    private String filepath;
    private String grade;
    private String provider;
}