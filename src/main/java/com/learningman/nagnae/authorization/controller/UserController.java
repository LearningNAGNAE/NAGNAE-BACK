package com.learningman.nagnae.authorization.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.learningman.nagnae.authorization.dto.UserLoginDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;
import com.learningman.nagnae.authorization.service.UserService;
import com.learningman.nagnae.util.JsonResult;
import com.learningman.nagnae.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/api/nagnae/users")
@RequiredArgsConstructor
public class UserController {
	
    private final UserService userService;
    
    // 로그인
    @PostMapping("/login/test")
    public ResponseEntity<JsonResult> SignUp(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
    	
    	log.info("user.login()");
    	
    	try {
            UserResponseDto authUser = userService.exeLogin(userLoginDto);
            if (authUser != null) {
                JwtUtil.createTokenAndSetHeader(response, String.valueOf(authUser.getUserID()));
                return ResponseEntity.ok(JsonResult.success(authUser));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(JsonResult.fail("로그인 실패: 잘못된 자격 증명"));
            }
        } catch (Exception e) {
            log.error("로그인 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(JsonResult.fail("서버 오류가 발생했습니다."+ e.getMessage()));
        }
        
    }
    
    

    
    
    
    

//    @PostMapping("/login")
//    public ResponseEntity<JsonResult> login(@RequestBody UserLoginDto loginDto) {
//        try {
//            UserResponseDto response = userService.login(loginDto);
//            return ResponseEntity.ok(JsonResult.success(response));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(JsonResult.fail(e.getMessage()));
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<JsonResult> getUser(@PathVariable Long id) {
//        try {
//            UserResponseDto user = userService.getUserById(id);
//            return ResponseEntity.ok(JsonResult.success(user));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                                 .body(JsonResult.fail(e.getMessage()));
//        }
//    }
}
