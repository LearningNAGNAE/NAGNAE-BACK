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
	private int USER_NO;
    private String USER_NAME;
    private String EMAIL;
    private String PASSWORD;
    private String NATIONLITY;
    private boolean ACTIVE_YN;
    private boolean WITHDRAW_YN;
    private boolean ANONYMIZE_YN;
    private int INSERT_USER_NO;
    private String INSERT_DATE;
    private int MODIFY_USER_NO;
    private String MODIFY_DATE;
}