package com.learningman.nagnae.authorization.service;

import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;

public interface UserService {
	// 로그인
    UserResponseDto exeLogin(UserDto loginDto);
    
    // 회원가입
    public void exeSignUp(UserDto SignUpDto);
}
