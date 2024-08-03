package com.learningman.nagnae.service.impl;


import org.springframework.stereotype.Service;

import com.learningman.nagnae.dto.UserLoginDto;
import com.learningman.nagnae.dto.UserResponseDto;
import com.learningman.nagnae.model.User;
import com.learningman.nagnae.repository.UserRepository;
import com.learningman.nagnae.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto login(UserLoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername());
        // 여기에 로그인 로직 구현 (비밀번호 검증 등)
        return convertToDto(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    private UserResponseDto convertToDto(User userVo) {
        return new UserResponseDto(userVo.getId(), userVo.getUsername(), userVo.getEmail());
    }
    

}