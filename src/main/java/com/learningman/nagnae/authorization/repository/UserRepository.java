package com.learningman.nagnae.authorization.repository;

import com.learningman.nagnae.authorization.dto.ProfileImgDto;
import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.model.ProfileImgVo;
import com.learningman.nagnae.authorization.model.User;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserRepository {
	
	// 로그인
    User findByUserLogin(UserDto loginDto);
    // 회원가입(사진x)
    void SignUp(UserDto signUpDto);
    // 회원가입유저 정보
    User InfoUser(String signUpUserEmail);
    // 프로필사진 등록
    ProfileImgDto profileImg(ProfileImgVo userImg);
    // 가입 유저 프로필 사진 정보
    ProfileImgDto InfoFile(int userNo);
    // 회원가입 유저 프로필 사진 번호 수정
    void userFileNoUpdate(User userInfo);
    
}