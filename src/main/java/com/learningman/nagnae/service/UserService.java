package com.learningman.nagnae.service;

import com.learningman.nagnae.dto.UserLoginDto;
import com.learningman.nagnae.dto.UserResponseDto;

public interface UserService {
    UserResponseDto login(UserLoginDto loginDto);
    UserResponseDto getUserById(Long id);
}
