package com.learningman.nagnae.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.learningman.nagnae.domain.dto.BoardDto;
import com.learningman.nagnae.domain.dto.BoardListDto;
import com.learningman.nagnae.domain.dto.BoardReadDto;
import com.learningman.nagnae.domain.dto.CommentDto;
import com.learningman.nagnae.domain.dto.FileDto;

@Mapper
public interface BoardMapper {

	int freeWrite(BoardDto freeboardDto);
	
	List<BoardListDto> freeList(@Param("categoryNo") int categoryNo,
            @Param("size") int size,
            @Param("offset") int offset,
            @Param("search") String search);

	int countPosts(@Param("categoryNo") int categoryNo, @Param("search") String search);
	
	int insertFile(FileDto fileDto);
	
	BoardReadDto selectBoardread(int boardno);
	
	void insertComment(CommentDto comment);
	
    int insertBoardComment(CommentDto commentDto);
    
    CommentDto getCommentById(Long commentno);

}
