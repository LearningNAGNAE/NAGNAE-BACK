package com.learningman.nagnae.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<JsonResult> SignUp(@RequestBody UserDto SignUpDto) {
    	
    	log.info("user.UserController.SignUp()");
    	userService.exeSignUp(SignUpDto);
    	
    	return ResponseEntity.ok(JsonResult.success("회원가입 성공"));
    }
    
    // 사진등록
    @PostMapping("/profile-img")
    public ResponseEntity<JsonResult> ProfileImg(@RequestParam("file") MultipartFile file) {
    	
    	log.info("user.UserController.ProfileImg()");
//    	System.out.println(file);
//    	userService.exeSignUp();
    	
    	return ResponseEntity.ok(JsonResult.success("프로필 사진 등록 완료"));
    }
    
    
    
    
//
//    @GetMapping("/{id}")
//    public ResponseEntity<responseMsg> getUser(@PathVariable Long id) {
//        try {
//            UserResponseDto user = userService.getUserById(id);
//            return ResponseEntity.ok(JsonResult.success(user));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                 .body(responseMsg.fail(e.getMessage()));
//        }
//    }
}
