package com.learningman.nagnae.board.repository;

import org.apache.ibatis.annotations.Mapper;

import com.learningman.nagnae.domain.dto.BoardDto;

@Mapper
public interface BoardMapper {

	int freeWrite(BoardDto freeboardDto);

}
