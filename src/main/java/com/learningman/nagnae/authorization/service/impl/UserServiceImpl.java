package com.learningman.nagnae.authorization.service.impl;


import org.springframework.stereotype.Service;

import com.learningman.nagnae.authorization.dto.UserLoginDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;
import com.learningman.nagnae.authorization.model.User;
import com.learningman.nagnae.authorization.repository.UserRepository;
import com.learningman.nagnae.authorization.service.UserService;

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
        return new UserResponseDto(user.getUSER_NO(), user.getUSER_NAME(), user.getEMAIL(), user.getPASSWORD());
    }
    

}