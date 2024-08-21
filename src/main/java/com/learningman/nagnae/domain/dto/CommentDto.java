package com.learningman.nagnae.domain.dto;

import lombok.Data;

@Data
public class CommentDto {
	
	private Long commentno;
    private Long boardno;
    private String commenter;
    private String content;
    private int commenterno;

}
