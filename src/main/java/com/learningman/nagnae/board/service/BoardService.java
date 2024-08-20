package com.learningman.nagnae.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.learningman.nagnae.board.repository.BoardMapper;
import com.learningman.nagnae.common.FileService;
import com.learningman.nagnae.domain.dto.BoardDto;
import com.learningman.nagnae.domain.dto.BoardListDto;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardMapper boardmapper;
    private final FileService fileService;
	
	public int exeBoardFreeWrite (BoardDto freeboardDto) {
		System.out.println("BoardService.exeBoardFreeWrite()");
		
		int count = boardmapper.freeWrite(freeboardDto);
		
		return count;
	}
	
	public List<BoardListDto> exeBoardFreeList(int categoryNo, int page, int size, String search) {
		System.out.println("BoardService.exeBoardFreeList()");
		
		int offset = (page - 1) * size;
		
        return boardmapper.freeList(categoryNo, size, offset, search);
	}
	
	public int getTotalPosts(int categoryNo, String search) {
        return boardmapper.countPosts(categoryNo, search);
    }
	
	public int getTotalPages(int categoryNo, int pageSize, String search) {
        int totalPosts = boardmapper.countPosts(categoryNo,search);
        return (int) Math.ceil((double) totalPosts / pageSize);
    }
	
	public String saveImageAndGetUrl(MultipartFile file) throws IOException {
		return fileService.saveImageAndGetUrl(file);
    }
	
	
}
