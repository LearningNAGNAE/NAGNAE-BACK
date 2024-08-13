package com.learningman.nagnae.authorization.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;
import com.learningman.nagnae.authorization.model.User;
import com.learningman.nagnae.authorization.repository.UserRepository;
import com.learningman.nagnae.authorization.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;

    // 로그인
    @Override
    public UserResponseDto exeLogin(UserDto loginDto) {
    	User authUser = userRepository.findByUserLogin(loginDto);
       
    	return convertToDto(authUser);
    }

    @Override
    public void exeSignUp(UserDto loginDto) {
        userRepository.SignUp(loginDto);
        
    }

    private UserResponseDto convertToDto(User user) {
        return new UserResponseDto(user.getUserno(), user.getUsername(), user.getEmail(), user.getPassword(), user.getUserhp(), user.getNationlity(), user.getFileno());
    }
    

}