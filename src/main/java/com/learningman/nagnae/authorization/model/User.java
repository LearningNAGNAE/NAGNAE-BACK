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