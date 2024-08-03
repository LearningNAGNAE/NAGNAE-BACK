package com.learningman.nagnae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.learningman.nagnae.dto.UserLoginDto;
import com.learningman.nagnae.dto.UserResponseDto;
import com.learningman.nagnae.service.UserService;
import com.learningman.nagnae.util.JsonResult;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JsonResult> login(@RequestBody UserLoginDto loginDto) {
        try {
            UserResponseDto response = userService.login(loginDto);
            return ResponseEntity.ok(JsonResult.success(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(JsonResult.fail(e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<JsonResult> getUser(@PathVariable Long id) {
        try {
            UserResponseDto user = userService.getUserById(id);
            return ResponseEntity.ok(JsonResult.success(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(JsonResult.fail(e.getMessage()));
        }
    }
}