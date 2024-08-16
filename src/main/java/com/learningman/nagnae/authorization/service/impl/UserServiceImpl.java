package com.learningman.nagnae.authorization.service.impl;


import java.util.UUID;
import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.learningman.nagnae.authorization.dto.ProfileImgDto;
import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;
import com.learningman.nagnae.authorization.model.ProfileImgVo;
import com.learningman.nagnae.authorization.model.User;
import com.learningman.nagnae.authorization.repository.UserRepository;
import com.learningman.nagnae.authorization.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;

    // 로그인
    @Override
    public UserResponseDto exeLogin(UserDto loginDto) {
    	User authUser = userRepository.findByUserLogin(loginDto);
    	return convertToDto(authUser);
    }

    // 회원가입
    @Override
    public void exeSignUp(UserDto loginDto, MultipartFile file) {
    	
    	// 회원가입(사진X)
        userRepository.SignUp(loginDto);
        
        String SignUpUserEmail = loginDto.getEmail();
        
        User userInfo= userRepository.InfoUser(SignUpUserEmail);
        
        int SignUpUserNo = userInfo.getUserno();
        
        // 사진 등록 및 회원가입 유저 사진번호 수정
        if(file != null) {
        	
        
	        // getOsSpecificSaveDir 로직
	        String osName = System.getProperty("os.name").toLowerCase();
	        String saveDir;
	        if (osName.contains("linux")) {
	            log.info("Operating system: Linux");
	            saveDir = "/app/upload";
	        } else {
	            log.info("Operating system: Windows");
	            saveDir = "C:\\Users\\hi02\\dev\\upload";
	        }
	
	        String orgName = file.getOriginalFilename();
	
	        // getFileExtension 로직
	        String exName = orgName.substring(orgName.lastIndexOf("."));
	
	        // generateSaveName 로직
	        String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
	
	        long fileSize = file.getSize();
	        String filePath = saveDir + File.separator + saveName;
	
	        log.info("File details - orgName: {}, exName: {}, saveName: {}, fileSize: {}, filePath: {}",
	                orgName, exName, saveName, fileSize, filePath);
	
	        ProfileImgVo profileImgVo = ProfileImgVo.builder()
	        	    .fileno(0)
	        	    .categoryno(1)
	        	    .savename(saveName)
	        	    .orgname(orgName)
	        	    .filepath(filePath)
	//        	    .filesize(fileSize)
	        	    .insertuserno(SignUpUserNo)
	        	    .insertdate(null)
	        	    .modifyuserno(SignUpUserNo)
	        	    .modifydate(null)
	        	    .build();
	        
	        log.info("ProfileImgVo: {}", profileImgVo);
	
	        userRepository.profileImg(profileImgVo);
	
	        // saveFile 로직
	        try (OutputStream os = new FileOutputStream(filePath);
	             BufferedOutputStream bos = new BufferedOutputStream(os)) {
	            bos.write(file.getBytes());
	        } catch (IOException e) {
	            log.error("Error saving file: {}", e.getMessage(), e);
	            throw new RuntimeException("파일 저장 중 오류 발생", e);
	        }
	        
	        ProfileImgDto ProfileFile= userRepository.InfoFile(SignUpUserNo);
	        
	        int SignUpFileNo = ProfileFile.getFileno();
	        
	        userInfo.setFileno(SignUpFileNo);
	        
	        userRepository.userFileNoUpdate(userInfo);
        
        }
        
    }

    private UserResponseDto convertToDto(User user) {
        return new UserResponseDto(user.getUserno(), user.getUsername(), user.getEmail(), user.getPassword(), user.getUserhp(), user.getNationlity(), user.getFileno());
    }
    

}