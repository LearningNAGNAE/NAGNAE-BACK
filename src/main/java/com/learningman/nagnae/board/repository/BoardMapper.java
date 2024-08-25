package com.learningman.nagnae.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.learningman.nagnae.domain.dto.BoardDto;
import com.learningman.nagnae.domain.dto.BoardListDto;
import com.learningman.nagnae.domain.dto.BoardReadDto;
import com.learningman.nagnae.domain.dto.CommentDto;
import com.learningman.nagnae.domain.dto.FileDto;
import com.learningman.nagnae.domain.dto.BoardFileDto;

@Mapper
public interface BoardMapper {

	int freeWrite(BoardDto freeboardDto);
	
	List<BoardListDto> freeList(@Param("categoryno") int categoryno,
            @Param("size") int size,
            @Param("offset") int offset,
            @Param("search") String search);

	int countPosts(@Param("categoryno") int categoryno, @Param("search") String search);
	
	int insertFile(FileDto fileDto);
	
	BoardReadDto selectBoardread(int boardno);
	
	void insertComment(CommentDto comment);
	
    int insertBoardComment(CommentDto commentDto);
    
    CommentDto getCommentById(Long commentno);
    
    List<CommentDto> boardcommentlist(Long boardno);
    
    int insertBoardFile(BoardFileDto boardFileDto);

    List<Integer> getFileByBoardNo(Long boardno);
    
    int boardupdate(BoardDto boardDto);     //업데이트

    void deleteBoardFile(Long boardno);

    void deleteFile(int fileNo);

    List<Integer> getCommentIdsByBoardNo(Long boardno);

    void deleteBoardComments(Long boardno);

    void deleteComment(int commentNo);

    void deleteBoard(Long boardno);

    int boardviewup(BoardDto boardDto);

    FileDto getFileByFileName(String fileName);
    
    List<BoardListDto> mainList(@Param("categoryNo") int categoryNo,
            							 @Param("size") int size);
    
    int countMainList(@Param("categoryno") int categoryno);

}
