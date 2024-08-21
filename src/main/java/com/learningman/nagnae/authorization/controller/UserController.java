package com.learningman.nagnae.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learningman.nagnae.authorization.dto.GoogleAuthResponseDto;
import com.learningman.nagnae.authorization.dto.GoogleTokenRequestDto;
import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;
import com.learningman.nagnae.authorization.model.User;
import com.learningman.nagnae.authorization.service.UserService;
import com.learningman.nagnae.authorization.util.JsonResult;
import com.learningman.nagnae.authorization.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Optional;

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
	        // 이메일로 사용자 조회
	        Optional<User> userOptional = userService.GoogleEmail(userLoginDto.getEmail());
	        
	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            
	            if ("GOOGLE".equals(user.getProvider())) {
	                // 구글 로그인 사용자의 경우, 일반 로그인 시도 시 에러 반환
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(JsonResult.fail("구글 계정으로 로그인하세요."));
	            } else {
	                // 일반 로그인 사용자의 경우
	                UserResponseDto authUser = userService.exeLogin(userLoginDto);
	                if (authUser != null) {
	                    JwtUtil.createTokenAndSetHeader(response, String.valueOf(authUser.getUserno()));
	                    return ResponseEntity.ok(JsonResult.success(authUser));
	                } else {
	                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(JsonResult.fail("로그인 실패: 잘못된 자격 증명"));
	                }
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(JsonResult.fail("로그인 실패: 사용자를 찾을 수 없음"));
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
    	System.out.println();
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
			UserDto modifyAccountDto = objectMapper.readValue(userInfoJson, UserDto.class);
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
    
    
    // 구글 로그인
    @PostMapping("/api/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleTokenRequestDto tokenRequest, HttpServletResponse response) {
        try {
            String tokenId = tokenRequest.getTokenId();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList("548018321247-nvrl8pv5juotgi2m09lvutofv37vjbfh.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(tokenId);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String userId = payload.getSubject();
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                // 사용자가 데이터베이스에 존재하는지 확인
                boolean isNewUser = !userService.userExists(email);
                // JWT 토큰 생성
                
                GoogleAuthResponseDto responseDto = new GoogleAuthResponseDto();
                if (isNewUser) {
                    // 새 사용자 정보 저장
                	User createGoogleUser = userService.createGoogleUser(userId, email, name);
                	
                	// JWT 토큰 생성
                    String jwtToken = JwtUtil.createToken(userId);
                	
                	
                    responseDto.setNewUser(isNewUser);
                    responseDto.setEmail(email);
                    responseDto.setName(name);
                    responseDto.setUserno(createGoogleUser.getUserno());
                    responseDto.setFileno(createGoogleUser.getFileno());
//                    System.out.println("================");
//                    System.out.println(responseDto);
//                    System.out.println("================");
                }else {
                	User createGoogleUser = userService.UserEmailInfo(email);
                	
                	// JWT 토큰 생성
                    String jwtToken = JwtUtil.createToken(userId);
                    
//                	System.out.println("================");
//                	System.out.println(createGoogleUser);
//                	System.out.println("================");
                	responseDto.setUserno(createGoogleUser.getUserno());
                    responseDto.setFileno(createGoogleUser.getFileno());
                    responseDto.setNewUser(isNewUser);
                    responseDto.setEmail(email);
                    responseDto.setName(name);
//                    System.out.println("================");
//                    System.out.println(responseDto);
//                    System.out.println("================");
                    
                }
                // JWT 토큰 생성 및 헤더에 추가
                JwtUtil.createTokenAndSetHeader(response, String.valueOf(responseDto.getUserno()));

                return ResponseEntity.ok(JsonResult.success(responseDto));
                
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
