package com.learningman.nagnae.authorization.service;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.learningman.nagnae.authorization.dto.EmailDto;
import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;
import com.learningman.nagnae.authorization.model.User;

public interface UserService {
	// 로그인
    UserResponseDto exeLogin(UserDto loginDto);
    
    // 회원가입
    void exeSignUp(UserDto signUpDto, MultipartFile file);
    
    // 회원정보 수정
    void exeModifyAccount(UserDto modifyAccountDto, MultipartFile file);
    
    // 로그인 유저 정보 
    UserResponseDto exeLoginUserInfo(UserDto loginDto);
    
    // 기존 아이디 여부
    boolean userExists(String email);
    
    // 구글아이디 가입
    User createGoogleUser(String userId, String email, String name);
    
    Optional<User> GoogleEmail(String email);
    
    UserDto createUserResponseDto(User user);
    
    User UserEmailInfo(String email);
    
    void FindId(EmailDto emailDto);
    
    void FindPw(EmailDto emailDto);
    
}
