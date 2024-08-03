package com.learningman.nagnae.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    private int UserID;
    private String UserName;
    private String Email;
    private String Password;
    private String Nationality;
    private boolean IsActive;
    private boolean IsDeleted;
    private boolean IsAnonymized;
    private String DeactivationDate;
    private String CreatedAt;
    private String UserOrgName;
    private String UserFilePath;
    private String UserSaveName;
    private long UserFileSize;
}