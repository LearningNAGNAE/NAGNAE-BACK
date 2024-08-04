package com.learningman.nagnae.repository;

import com.learningman.nagnae.dto.UserLoginDto;
import com.learningman.nagnae.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {
	Optional<User> findById(Long id);

    User findByUserLogin(UserLoginDto loginDto);
}