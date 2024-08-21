package com.learningman.nagnae.authorization.service.impl;


import java.util.Optional;
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
//    	System.out.println(authUser);
    	return convertToDto(authUser);
    }

    // 회원가입
    @Override
    public void exeSignUp(UserDto signUpDto, MultipartFile file) {
    	
    	// 회원가입(사진X)
        userRepository.SignUp(signUpDto);
        
        String SignUpUserEmail = signUpDto.getEmail();
        
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
	            saveDir = "C:\\Users\\hi02\\dev\\NAGNAE\\NAGNAE-FRONT\\src\\assets\\images\\img";
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

    // 회원정보수정
    
    // 회원수정
    @Override
    public void exeModifyAccount(UserDto modifyAccountDto, MultipartFile file) {
//    	System.out.println(modifyAccountDto);
//    	System.out.println(file);
    	// 회원정보(사진X)
        userRepository.ModifyAccount(modifyAccountDto);
        
        String ModifyAccountEmail = modifyAccountDto.getEmail();
        
        User userInfo= userRepository.InfoUser(ModifyAccountEmail);
        
        int ModifyAccountNo = userInfo.getUserno();
        
        // 사진 등록 및 회원수정 유저 사진번호 수정
        if(file != null) {
        	
        	// 기존 유저 사진 번호 null번경
        	userRepository.UserImgNoNull(ModifyAccountNo);
        	
        	// 기존 유저 원래 사진 삭제
	        userRepository.UserFileDelete(ModifyAccountNo);
        	
	        // getOsSpecificSaveDir 로직
	        String osName = System.getProperty("os.name").toLowerCase();
	        String saveDir;
	        if (osName.contains("linux")) {
	            log.info("Operating system: Linux");
	            saveDir = "/app/upload";
	        } else {
	            log.info("Operating system: Windows");
	            saveDir = "C:\\Users\\hi02\\dev\\NAGNAE\\NAGNAE-FRONT\\src\\assets\\images\\profile";
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
	        	    .insertuserno(ModifyAccountNo)
	        	    .insertdate(null)
	        	    .modifyuserno(ModifyAccountNo)
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
	        
	        ProfileImgDto ProfileFile= userRepository.InfoFile(ModifyAccountNo);
	        
	        int ModifyAccountFileNo = ProfileFile.getFileno();
	        
	        
	        
	        userInfo.setFileno(ModifyAccountFileNo);
	        
	        userRepository.userFileNoUpdate(userInfo);
	        
	        
        
        }
        
        
    }
    
    // 로그인한 회원의 정보
    @Override
    public UserResponseDto exeLoginUserInfo(UserDto loginDto) {
    	User authUser = userRepository.loginUserInfo(loginDto);
//    	System.out.println(authUser);
    	return convertToDto(authUser);
    }
    
    // 구글 로그인
    @Override
    public boolean userExists(String email) {
        // 데이터베이스에서 이메일로 사용자 존재 여부 확인
    	return userRepository.findByEmail(email).isPresent();
    }
    
    @Override
    public User createGoogleUser(String userId, String email, String name) {
    	// 새 Google 사용자 정보를 데이터베이스에 저장
    	User newUser = new User();
        newUser.setUserno(0);  // Auto-increment이므로 null 설정
        newUser.setUsername(name);
        newUser.setGrade("일반");  // 또는 Google 사용자에 대한 특정 등급
        newUser.setEmail(email);
        newUser.setPassword(""); // Google 로그인이므로 비밀번호 불필요, 하지만 NOT NULL 제약조건 때문에 빈 문자열 입력
        newUser.setNationlity(""); // 기본값 설정 필요
        newUser.setFileno(0);
        newUser.setUserhp(""); // Google에서 전화번호를 제공하지 않으므로 빈 문자열 또는 null 설정
        newUser.setProvider("GOOGLE");
        userRepository.createGoogleUser(newUser);
        
        User insertedUser = userRepository.selectUserById(newUser.getUserno());
    	
        return insertedUser;
    }
    
    @Override
    public Optional<User> GoogleEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto createUserResponseDto(User user) {
        // User 객체를 UserResponseDto로 변환하는 로직
    	UserDto dto = new UserDto();
        dto.setUserno(user.getUserno());
        dto.setEmail(user.getEmail());
        // 필요한 다른 필드들 설정
        return dto;
    }
    
    @Override
    public User UserEmailInfo(String email) {
        // 데이터베이스에서 이메일로 사용자 존재 여부 확인
    	return userRepository.UserEmailInfo(email);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private UserResponseDto convertToDto(User user) {
        return new UserResponseDto(
            user.getUserno(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getNationlity(),        // 수정된 순서
            user.isActiveyn(),           // 추가된 필드
            user.isWithdrawyn(),         // 추가된 필드
            user.isAnonymizeyn(),        // 추가된 필드
            user.getInsertuserno(),
            user.getInsertdate(),
            user.getModifyuserno(),
            user.getModifydate(),
            user.getUserhp(),
            user.getFileno(),
            user.getCategoryno(),
            user.getSavename(),
            user.getOrgname(),
            user.getFilepath(),
            user.getGrade(),             // 추가된 필드
            user.getProvider()           // 추가된 필드
        );
    }
    

}