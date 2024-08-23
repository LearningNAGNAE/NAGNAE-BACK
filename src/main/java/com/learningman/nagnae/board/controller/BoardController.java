package com.learningman.nagnae.board.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learningman.nagnae.board.service.BoardService;
import com.learningman.nagnae.domain.dto.BoardDto;
import com.learningman.nagnae.domain.dto.BoardListDto;
import com.learningman.nagnae.domain.dto.BoardReadDto;
import com.learningman.nagnae.domain.dto.CommentDto;
import com.learningman.nagnae.domain.response.ResponseMsg;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;

	@PostMapping("/freeboardwrite")
	public ResponseEntity<ResponseMsg> BoardFreeWrite(@RequestBody BoardDto freeboardDto) {
		System.out.println("BoardController.BoardFreeWrite()");
		
		freeboardDto.setCategoryno(1);
		freeboardDto.setViews(0);
		int count = boardService.exeBoardFreeWrite(freeboardDto);
		
		if(count == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(ResponseMsg.success(count));
	}
	
	@GetMapping("/freeboardlist")
	public ResponseEntity<ResponseMsg> BoardFreeList(@RequestParam(value = "categoryNo", defaultValue = "1") int categoryNo,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "search", defaultValue = "") String search) {
		System.out.println("BoardController.BoardFreeList()");
		
		page = Math.max(page, 1);
		
		List<BoardListDto> boardList = boardService.exeBoardFreeList(categoryNo, page, size, search);
		
		int totalPosts = boardService.getTotalPosts(categoryNo, search); 
		int totalPages = boardService.getTotalPages(categoryNo, size, search);
		
		Map<String, Object> response = new HashMap<>();
	    response.put("boardList", boardList);
	    response.put("totalPages", totalPages);
	    response.put("totalPosts", totalPosts);
	    response.put("pageSize", size);
		
		return ResponseEntity.ok(ResponseMsg.success(response));
	}
	
	@PostMapping("/upload-image")
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
	    try {
	        String imageUrl = boardService.saveImageAndGetUrl(file);
	        return ResponseEntity.ok(Collections.singletonMap("imageUrl", imageUrl));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Collections.singletonMap("error", e.getMessage()));
	    }
	}
	
	
	@GetMapping("/freeboardread")
    public ResponseEntity<ResponseMsg> BoardRead(@RequestParam("boardno") int boardno) {
		System.out.println("BoardController.BoardRead()");
//		System.out.println(boardno);
//        int boardno = Integer.parseInt(sboardno);
		BoardReadDto boardwriteDto = boardService.exeBoardRead(boardno);
		
		return ResponseEntity.ok(ResponseMsg.success(boardwriteDto));
    }
	
	@PostMapping("/freeboardcommentwrite")
    public ResponseEntity<ResponseMsg> BoardFreeCommentWrite(@RequestBody CommentDto commentDto) {
        System.out.println("BoardController.BoardFreeCommentWrite()");
        System.out.println(commentDto);
        // 댓글을 삽입하고 게시물과 연결합니다.
        int count = boardService.exeBoardFreeCommentWrite(commentDto);
        
        if (count == 0) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(ResponseMsg.success(count));
    }
	
	@GetMapping("/freeboardcommentlist")
    public ResponseEntity<ResponseMsg> CommentList(@RequestParam("boardno") Long boardno) {
		System.out.println("BoardController.CommentList()");
//		System.out.println(boardno);
//        int boardno = Integer.parseInt(sboardno);
		List<CommentDto> commentDto = boardService.exeboardcommentlist(boardno);
		
		return ResponseEntity.ok(ResponseMsg.success(commentDto));
    }
}
