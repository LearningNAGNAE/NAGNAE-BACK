package com.learningman.nagnae.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.learningman.nagnae.board.repository.BoardMapper;
import com.learningman.nagnae.common.FileService;
import com.learningman.nagnae.domain.dto.BoardDto;
import com.learningman.nagnae.domain.dto.FileDto;
import com.learningman.nagnae.domain.dto.BoardListDto;
import com.learningman.nagnae.domain.dto.BoardReadDto;
import com.learningman.nagnae.domain.dto.CommentDto;

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
        String imageUrl = fileService.saveImageAndGetUrl(file);
        FileDto fileDto = new FileDto();
        fileDto.setCategoryNo(1); // 적절한 카테고리 번호 설정
        fileDto.setFileOriginName(file.getOriginalFilename());
        fileDto.setFileSaveName(imageUrl.substring(imageUrl.lastIndexOf('/') + 1));
        fileDto.setFilePath(imageUrl);
        boardmapper.insertFile(fileDto);
        return imageUrl;
    }
	
	public BoardReadDto exeBoardRead(int boardno) {

        return boardmapper.selectBoardread(boardno);
    }
	
	public int exeBoardFreeCommentWrite(CommentDto commentDto) {
        // 댓글을 삽입합니다.
		boardmapper.insertComment(commentDto);
        
        // 삽입된 댓글의 ID를 설정합니다.
        Long commentno = commentDto.getCommentno();
        commentDto.setCommentno(commentno);
        
        // 댓글과 게시물을 연결합니다.
        int count = boardmapper.insertBoardComment(commentDto);
        
        return count;
    }
	
	public List<CommentDto> exeboardcommentlist(Long boardno) {

        return boardmapper.boardcommentlist(boardno);
    }
	
	public int exeBoardDelete(BoardDto boardDto) {
		
		return boardmapper.boarddelete(boardDto);
	}
	
}
