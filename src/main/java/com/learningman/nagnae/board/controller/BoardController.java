package com.learningman.nagnae.board.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.learningman.nagnae.domain.dto.FileDto;
import com.learningman.nagnae.domain.response.ResponseMsg;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;

	//---------------------------Write ------------------------------
	//커뮤니티 게시글 작성
	@PostMapping("/freeboardwrite")
	public ResponseEntity<ResponseMsg> BoardFreeWrite(@RequestBody BoardDto freeboardDto, 
	                                                  @RequestParam(value = "files", required = false) List<MultipartFile> files) {
	    System.out.println("BoardController.BoardFreeWrite()");
	    
	    freeboardDto.setCategoryno(1);
	    freeboardDto.setViews(0);
	    int count = boardService.exeBoardFreeWrite(freeboardDto);
	    
	    if(count == 0) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    return ResponseEntity.ok(ResponseMsg.success(count));
	}
	
	//공지사항 게시글 작성
	@PostMapping("/annboardwrite")
	public ResponseEntity<ResponseMsg> BoardAnnWrite(@RequestBody BoardDto freeboardDto, 
	                                                  @RequestParam(value = "files", required = false) List<MultipartFile> files) {
	    System.out.println("BoardController.BoardFreeWrite()");
	    
	    freeboardDto.setCategoryno(2);
	    freeboardDto.setViews(0);
	    int count = boardService.exeBoardFreeWrite(freeboardDto);
	    
	    if(count == 0) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    return ResponseEntity.ok(ResponseMsg.success(count));
	}
	
	//게시글 작성 -- 이미지 업로드
	@PostMapping("/upload-image")
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, 
	                                     @RequestParam(value = "userNo", required = false) String stringUserNo) {
	    try {
	        int userNo;
	        if (stringUserNo == null || stringUserNo.isEmpty() || stringUserNo.equals("undefined")) {
	            userNo = 36; // Default value
	            System.out.println("Default userNo: " + userNo);
	        } else {
	            userNo = Integer.parseInt(stringUserNo);
	            System.out.println("UserNo: " + userNo);
	        }
	        
	        String imageUrl = boardService.saveImageAndGetUrl(file);

	        FileDto fileDto = new FileDto();
	        fileDto.setCategoryNo(1);
	        fileDto.setFileOriginName(file.getOriginalFilename());
	        fileDto.setFileSaveName(imageUrl.substring(imageUrl.lastIndexOf('/') + 1));
	        fileDto.setFilePath(imageUrl);
	        fileDto.setInsertUserNo(userNo);
	        fileDto.setModifyUserNo(userNo);
	        boardService.insertFile(fileDto);

	        return ResponseEntity.ok(Collections.singletonMap("imageUrl", imageUrl));
	    } catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().body("Invalid userNo format");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Collections.singletonMap("error", e.getMessage()));
	    }
	}

	//---------------------------List ------------------------------
	//커뮤니티 게시판
	@GetMapping("/freeboardlist")
	public ResponseEntity<ResponseMsg> BoardFreeList(@RequestParam(value = "categoryno", defaultValue = "1") int categoryno,
											         @RequestParam(value = "page", defaultValue = "1") int page,
											         @RequestParam(value = "size", defaultValue = "10") int size,
											         @RequestParam(value = "search", defaultValue = "") String search) {
		System.out.println("BoardController.BoardFreeList()");
		
		page = Math.max(page, 1);
		
		List<BoardListDto> boardList = boardService.exeBoardFreeList(categoryno, page, size, search);
		
		int totalPosts = boardService.getTotalPosts(categoryno, search); 
		int totalPages = boardService.getTotalPages(categoryno, size, search);
		
		Map<String, Object> response = new HashMap<>();
	    response.put("boardList", boardList);
	    response.put("totalPages", totalPages);
	    response.put("totalPosts", totalPosts);
	    response.put("pageSize", size);
		
		return ResponseEntity.ok(ResponseMsg.success(response));
	}
	
	// 공지사항 리스트
	@GetMapping("/annboardlist")
	public ResponseEntity<ResponseMsg> BoardAnnList(@RequestParam(value = "categoryNo", defaultValue = "2") int categoryNo,
	        @RequestParam(value = "page", defaultValue = "1") int page,
	        @RequestParam(value = "size", defaultValue = "10") int size,
	        @RequestParam(value = "search", defaultValue = "") String search) {
		System.out.println("BoardController.BoardAnnList()");
		
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
	

	
	
	//---------------------------Read ------------------------------
	// 커뮤니티 읽어오기
	@GetMapping("/freeboardread")
    public ResponseEntity<ResponseMsg> BoardRead(@RequestParam("boardno") int boardno) {
		System.out.println("BoardController.BoardRead()");
		System.out.println(boardno);
//        int boardno = Integer.parseInt(sboardno);
		BoardReadDto boardwriteDto = boardService.exeBoardRead(boardno);
		
		return ResponseEntity.ok(ResponseMsg.success(boardwriteDto));
    }
	// 커뮤니티 댓글 작성
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
	
	// 커뮤니티 댓글 리스트
	@GetMapping("/freeboardcommentlist")
    public ResponseEntity<ResponseMsg> CommentList(@RequestParam("boardno") Long boardno) {
		System.out.println("BoardController.CommentList()");
//		System.out.println(boardno);
//        int boardno = Integer.parseInt(sboardno);
		List<CommentDto> commentDto = boardService.exeboardcommentlist(boardno);
		
		return ResponseEntity.ok(ResponseMsg.success(commentDto));
    }
	
	//--------------------------- Delete ------------------------------
	@DeleteMapping("/freereaddelete")
	public ResponseEntity<ResponseMsg> BoardReadDelte(@RequestParam("boardno") Long boardno) {
		System.out.println("BoardController.BoardReadDelte()");
		
		boardService.exeBoardDelete(boardno);
		
		return ResponseEntity.ok(ResponseMsg.success("성공"));
	}
	
	//--------------------------- Hits ------------------------------
	@PutMapping("/incrementViews")
	public ResponseEntity<ResponseMsg> BoardViewsUp(@RequestBody BoardDto boardDto){
		System.out.println("BoardController.BoardViewsUp()");
		System.out.println(boardDto);
		
		int count = boardService.exeBoardViewUp(boardDto);
		
		return ResponseEntity.ok(ResponseMsg.success(count));	
	}
	
	
	//--------------------------- Main List ------------------------------
	@GetMapping("/announcements6")
    public ResponseEntity<ResponseMsg> MainAnnouncements(@RequestParam(value = "categoryno", defaultValue = "2") int categoryno,
    												 	 @RequestParam(value = "size", defaultValue = "6") int size) {
		System.out.println("BoardController.MainAnnouncements()");

		List<BoardListDto> boardList = boardService.exeMainList(categoryno,size);
		
		int totalMainAnnouncements = boardService.getTotalboardMain(categoryno);
		
		Map<String, Object> response = new HashMap<>();
	    response.put("boardList", boardList);
	    response.put("totalMainCount", totalMainAnnouncements);
		
		return ResponseEntity.ok(ResponseMsg.success(response));
    }
	
	@GetMapping("/community6")
    public ResponseEntity<ResponseMsg> MainCommunity(@RequestParam(value = "categoryno", defaultValue = "1") int categoryno,
    												 @RequestParam(value = "size", defaultValue = "6") int size) {
		System.out.println("BoardController.MainCommunity()");

		List<BoardListDto> boardList = boardService.exeMainList(categoryno,size);
		
		int totalMainCommunity = boardService.getTotalboardMain(categoryno);
		
		Map<String, Object> response = new HashMap<>();
	    response.put("boardList", boardList);
	    response.put("totalMainCount", totalMainCommunity);
		
		return ResponseEntity.ok(ResponseMsg.success(response));
    }
}
