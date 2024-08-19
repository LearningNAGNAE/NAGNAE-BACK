package com.learningman.nagnae.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;
import com.learningman.nagnae.authorization.service.UserService;
import com.learningman.nagnae.authorization.util.JsonResult;
import com.learningman.nagnae.authorization.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private UserService userService;

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<JsonResult> SignIn(@RequestBody UserDto userLoginDto, HttpServletResponse response) {

		log.info("user.UserController.SignIn()");
		try {
			UserResponseDto authUser = userService.exeLogin(userLoginDto);
			if (authUser != null) {
				JwtUtil.createTokenAndSetHeader(response, String.valueOf(authUser.getUserno()));
				return ResponseEntity.ok(JsonResult.success(authUser));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(JsonResult.fail("로그인 실패: 잘못된 자격 증명"));
			}
		} catch (Exception e) {
			log.error("로그인 처리 중 오류 발생", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(JsonResult.fail("서버 오류가 발생했습니다." + e.getMessage()));
		}
	}

	// 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<JsonResult> SignUp(@RequestPart("userInfo") String userInfoJson, @RequestPart(value = "file", required = false) MultipartFile file) {
    	log.info("user.UserController.SignUp()");
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
			UserDto SignUpDto = objectMapper.readValue(userInfoJson, UserDto.class);
			
			userService.exeSignUp(SignUpDto, file);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return ResponseEntity.ok(JsonResult.success("회원가입 성공"));
    }
    
	// 회원정보수정
    @PutMapping("/modify-account")
    public ResponseEntity<JsonResult> ModifyAccount(@RequestPart("userInfo") String userInfoJson, @RequestPart(value = "file", required = false) MultipartFile file) {
    	log.info("user.UserController.ModifyAccount()");
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
			UserDto modifyAccountDto = objectMapper.readValue(userInfoJson, UserDto.class);
			System.out.println();
			userService.exeModifyAccount(modifyAccountDto, file);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return ResponseEntity.ok(JsonResult.success("회원정보 수정 성공"));
    }
    
    // 로그인유저정보
    @PostMapping("/one-user-info")
    public ResponseEntity<JsonResult> OneUserInfo(@RequestBody UserDto userLoginInfo) {
    	log.info("user.UserController.OneUserInfo()");
        try {
        	
        	UserResponseDto loginUser = userService.exeLoginUserInfo(userLoginInfo);
        	
        	return ResponseEntity.ok(JsonResult.success(loginUser));
        } catch (Exception e) {
			log.error("오류 발생", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(JsonResult.fail("서버 오류가 발생했습니다." + e.getMessage()));
		}
//    	return ResponseEntity.ok(JsonResult.success("회원정보 수정 성공"));
        
//        return null;
    }
    
    

}
