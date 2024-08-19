package com.learningman.nagnae.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	private int userno;
    private String username;
    private String email;
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
}