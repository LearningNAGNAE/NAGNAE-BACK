package com.learningman.nagnae.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learningman.nagnae.board.servie.BoardService;
import com.learningman.nagnae.domain.dto.BoardDto;
import com.learningman.nagnae.util.JsonResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;

	@PostMapping("/freeboardwrite")
	public ResponseEntity<JsonResult> BoardFreeWrite (@RequestBody BoardDto freeboardDto) {
		System.out.println("BoardController.BoardFreeWrite()");
		
		freeboardDto.setBoardid(2);
		freeboardDto.setHits(0);
		int count = boardService.exeBoardFreeWrite(freeboardDto);
		
		if(count == 0) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(JsonResult.success(count));
	}
	
}
