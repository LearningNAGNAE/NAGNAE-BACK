package com.learningman.nagnae.board.servie;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learningman.nagnae.board.repository.BoardMapper;
import com.learningman.nagnae.domain.dto.BoardDto;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardMapper boardmapper;
	
	public int exeBoardFreeWrite (BoardDto freeboardDto) {
		System.out.println("BoardService.exeBoardFreeWrite()");
		
		int count = boardmapper.freeWrite(freeboardDto);
		
		return count;
	}

}
