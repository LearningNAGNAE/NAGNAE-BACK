package com.learningman.nagnae.service;

import com.learningman.nagnae.dto.UserLoginDto;
import com.learningman.nagnae.dto.UserResponseDto;

public interface UserService {
	// 로그인
    UserResponseDto exeLogin(UserLoginDto loginDto);
    
    // 
    UserResponseDto getUserById(Long id);
}
