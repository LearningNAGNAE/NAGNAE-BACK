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

    // 로그인
    @Override
    public UserResponseDto exeLogin(UserLoginDto loginDto) {
    	System.out.println(loginDto);
    	User authUser = userRepository.findByUserLogin(loginDto);
       
    	return convertToDto(authUser);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    private UserResponseDto convertToDto(User user) {
        return new UserResponseDto(user.getUserID(), user.getUserName(), user.getEmail(), user.getPassword());
    }
    

}