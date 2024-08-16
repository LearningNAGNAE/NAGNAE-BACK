package com.learningman.nagnae.board.controller;

import com.learningman.nagnae.domain.response.ResponseMsg;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learningman.nagnae.board.servie.BoardService;
import com.learningman.nagnae.domain.dto.BoardDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;

	@PostMapping("/freeboardwrite")
	public ResponseEntity<ResponseMsg> BoardFreeWrite (@RequestBody BoardDto freeboardDto) {
		System.out.println("BoardController.BoardFreeWrite()");
		
		freeboardDto.setCategoryno(2);
		freeboardDto.setViews(0);
		int count = boardService.exeBoardFreeWrite(freeboardDto);
		
		if(count == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(ResponseMsg.success(count));
	}
	
	@PostMapping("/upload-image")
	public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile imageFile) {
	    try {
	        String saveDir;

	        String osName = System.getProperty("os.name").toLowerCase();
	        if (osName.contains("linux")) {
	            saveDir = "/app/upload/"; // Linux 경로
	        } else {
	            saveDir = "C:\\javaStudy\\upload\\"; // Windows 경로
	        }

	        String fileName = imageFile.getOriginalFilename();
	        Path filePath = Paths.get(saveDir, fileName);

	        Files.write(filePath, imageFile.getBytes());

	        String imageUrl = "/upload/" + fileName; // 업로드된 이미지에 접근할 수 있는 URL

	        Map<String, String> response = new HashMap<>();
	        response.put("imageUrl", imageUrl);

	        return ResponseEntity.ok(response);

	    } catch (IOException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Collections.singletonMap("error", "File upload failed"));
	    }
	}

	        
	       

	
}
