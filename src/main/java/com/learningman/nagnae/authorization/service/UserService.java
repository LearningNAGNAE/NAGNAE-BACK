package com.learningman.nagnae.authorization.service;

import com.learningman.nagnae.authorization.dto.UserLoginDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;

public interface UserService {
	// 로그인
    UserResponseDto exeLogin(UserLoginDto loginDto);
    
    
}
