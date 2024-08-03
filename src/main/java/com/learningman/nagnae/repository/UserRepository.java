package com.learningman.nagnae.repository;

import com.learningman.nagnae.model.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserRepository {
	Optional<UserVo> findById(Long id);

    UserVo findByUsername(String username);
}