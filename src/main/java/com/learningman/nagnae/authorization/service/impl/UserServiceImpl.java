package com.learningman.nagnae.authorization.service.impl;


import java.util.Optional;
import java.util.UUID;
import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.learningman.nagnae.authorization.dto.EmailDto;
import com.learningman.nagnae.authorization.dto.ProfileImgDto;
import com.learningman.nagnae.authorization.dto.UserDto;
import com.learningman.nagnae.authorization.dto.UserResponseDto;
import com.learningman.nagnae.authorization.model.ProfileImgVo;
import com.learningman.nagnae.authorization.model.User;
import com.learningman.nagnae.authorization.repository.UserRepository;
import com.learningman.nagnae.authorization.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 로그인
	@Override
	public UserResponseDto exeLogin(UserDto loginDto) {
	    User authUser = userRepository.findByUserLogin(loginDto);
	    if (authUser != null && passwordEncoder.matches(loginDto.getPassword(), authUser.getPassword())) {
	        return convertToDto(authUser);
	    }
	    return null; // 로그인 실패 시 null 반환
	}

    // 회원가입
    @Override
    public void exeSignUp(UserDto signUpDto, MultipartFile file) {
    	
    	// 비밀번호 암호화
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        
        // 회원가입(사진X)
        
        userRepository.SignUp(signUpDto);
        userRepository.updateUserNoAfterSignUp(signUpDto.getUserno());
        
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

    // 회원수정
    @Override
    public void exeModifyAccount(UserDto modifyAccountDto, MultipartFile file) {
    	// 기존 사용자 정보 조회
        User existingUser = userRepository.InfoUser(modifyAccountDto.getEmail());
        System.out.println("====================");
        System.out.println(modifyAccountDto);
        System.out.println("====================");
        
        // 비밀번호 처리
        if (modifyAccountDto.getPassword() == null || modifyAccountDto.getPassword().isEmpty()) {
            // 비밀번호가 null이거나 빈 문자열이면 기존 비밀번호 사용
            modifyAccountDto.setPassword(existingUser.getPassword());
        } else {
            // 새 비밀번호가 제공된 경우 암호화
            modifyAccountDto.setPassword(passwordEncoder.encode(modifyAccountDto.getPassword()));
        }

        // 회원정보 업데이트 (사진 제외)
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
        newUser.setPassword(passwordEncoder.encode("defaultPassword")); // Google 로그인이므로 비밀번호 불필요, 하지만 NOT NULL 제약조건 때문에 빈 문자열 입력
        newUser.setNationlity(""); // 기본값 설정 필요
        newUser.setFileno(0);
        newUser.setUserhp(""); // Google에서 전화번호를 제공하지 않으므로 빈 문자열 또는 null 설정
        newUser.setProvider("GOOGLE");
        newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); // 랜덤한 비밀번호 생성
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
    
    // 아이디 찾기
    @Override
    public void FindId(EmailDto emailDto) {
    	
    	User findIdUserInfo = userRepository.FindIdUserInfo(emailDto);
    	
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getTo());
        message.setSubject(emailDto.getSubject());
        message.setText("You ID : " + findIdUserInfo.getEmail());
        emailSender.send(message);
        
    }
    
    // 비밀번호 찾기(임시비밀번호 메일로 보냄)
    @Override
    public void FindPw(EmailDto emailDto) {
    	User findPwUserInfo = userRepository.FindPwUserInfo(emailDto);
    	
    	// 임시 비밀번호 생성(랜덤)
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        StringBuilder tempPwBuilder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int idx = (int) (charSet.length * Math.random());
            tempPwBuilder.append(charSet[idx]);
        }
    	
        // StringBuilder를 String으로 변환
        String tempPw = tempPwBuilder.toString();
        
        findPwUserInfo.setPassword(tempPw);
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getTo());
        message.setSubject(emailDto.getSubject());
        message.setText("You PW : " + tempPw);
        emailSender.send(message);
        
        // 비밀밀번호 엔코딩 및 변경
        findPwUserInfo.setPassword(passwordEncoder.encode(findPwUserInfo.getPassword()));
        
        
        userRepository.FindUserPwUpdate(findPwUserInfo);
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    private UserResponseDto convertToDto(User user) {
        return new UserResponseDto(
            user.getUserno(),
            user.getUsername(),
            user.getEmail(),
            null, // 비밀번호는 응답에 포함하지 않음
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