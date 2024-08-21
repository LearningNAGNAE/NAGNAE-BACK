package com.learningman.nagnae.authorization.repository;

import com.learningman.nagnae.authorization.dto.ProfileImgDto;
import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.model.ProfileImgVo;
import com.learningman.nagnae.authorization.model.User;

import java.util.Optional;

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
    void profileImg(ProfileImgVo userImg);
    // 가입 유저 프로필 사진 정보
    ProfileImgDto InfoFile(int userNo);
    // 회원 유저 프로필 사진 번호 수정
    void userFileNoUpdate(User userInfo);
    // 회원정보수정(사진x)
    void ModifyAccount(UserDto modifyAccountDto);
    // 기존 유저 프로필 사진번호 null변경
    void UserImgNoNull(int UserImgNoNull);
    // 기존 유저 프로필 사진 삭제
    void UserFileDelete(int UserFileDelete);
    // 로그인한 회원정보
    User loginUserInfo(UserDto loginDto);
    // 구글 아이디 여부
    Optional<User> findByEmail(String email);
    // 구글 아이디 생성
    void createGoogleUser(User user);
    
    User selectUserById(int userno);
    
    User UserEmailInfo(String email);
    
}