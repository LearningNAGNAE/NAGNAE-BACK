package com.learningman.nagnae.authorization.repository;

import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.model.User;

import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {

    User findByUserLogin(UserDto loginDto);
    
    public void SignUp(UserDto loginDto);
    
}