package com.learningman.nagnae.authorization.service;

import org.springframework.web.multipart.MultipartFile;

import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;

public interface UserService {
	// 로그인
    UserResponseDto exeLogin(UserDto loginDto);
    
    // 회원가입
    void exeSignUp(UserDto signUpDto, MultipartFile file);
}
