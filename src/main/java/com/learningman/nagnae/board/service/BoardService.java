package com.learningman.nagnae.board.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.learningman.nagnae.board.repository.BoardMapper;
import com.learningman.nagnae.common.FileService;
import com.learningman.nagnae.domain.dto.BoardDto;
import com.learningman.nagnae.domain.dto.BoardFileDto;
import com.learningman.nagnae.domain.dto.BoardListDto;
import com.learningman.nagnae.domain.dto.BoardReadDto;
import com.learningman.nagnae.domain.dto.CommentDto;
import com.learningman.nagnae.domain.dto.FileDto;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardMapper boardmapper;
    private final FileService fileService;
	
    @Transactional
    public int exeBoardFreeWrite(BoardDto boardDto) {
        int count = boardmapper.freeWrite(boardDto);
        
        System.out.println("게시물 번호: " + boardDto.getBoardno());

        List<String> imageUrls = extractImageUrls(boardDto.getContent());
        
        for (String imageUrl : imageUrls) {
            try {
                String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
                FileDto fileDto = boardmapper.getFileByFileName(fileName);
                if (fileDto != null) {
                    BoardFileDto boardFileDto = new BoardFileDto();
                    boardFileDto.setBoardNo(boardDto.getBoardno());
                    boardFileDto.setFileNo(fileDto.getFileNo());
                    boardFileDto.setInsertUserNo(boardDto.getInsertuserno());
                    boardFileDto.setModifyUserNo(boardDto.getModifyuserno());
                    int insertedRows = boardmapper.insertBoardFile(boardFileDto);
                    if (insertedRows == 0) {
                        throw new RuntimeException("TBOARDFILE 테이블에 데이터를 삽입하지 못했습니다.");
                    }
                    System.out.println("TBOARDFILE에 데이터 삽입 성공: " + boardFileDto);
                } else {
                    System.out.println("파일을 찾을 수 없습니다: " + fileName);
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("예상치 못한 오류가 발생했습니다.", e);
            }
        }

        return count;
    }

    private List<String> extractImageUrls(String content) {
        List<String> urls = new ArrayList<>();
        Pattern pattern = Pattern.compile("<img.*?src=\"(.*?)\".*?>");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            urls.add(matcher.group(1));
        }
        return urls;
    }
    
    public void insertFile(FileDto fileDto) {
        boardmapper.insertFile(fileDto);
    }
    
    public String saveImageAndGetUrl(MultipartFile file) throws IOException {
        return fileService.saveImageAndGetUrl(file);
    }
    
	
	public List<BoardListDto> exeBoardFreeList(int categoryno, int page, int size, String search) {
		System.out.println("BoardService.exeBoardFreeList()");
		
		int offset = (page - 1) * size;
		
        return boardmapper.freeList(categoryno, size, offset, search);
	}
	
	public int getTotalPosts(int categoryNo, String search) {
        return boardmapper.countPosts(categoryNo, search);
    }
	
	public int getTotalPages(int categoryNo, int pageSize, String search) {
        int totalPosts = boardmapper.countPosts(categoryNo,search);
        return (int) Math.ceil((double) totalPosts / pageSize);
    }

//    public String saveImageAndGetUrl(MultipartFile file) throws IOException {
//        String imageUrl = fileService.saveImageAndGetUrl(file);
//        FileDto fileDto = new FileDto();
//        fileDto.setCategoryNo(1); // 적절한 카테고리 번호 설정
//        fileDto.setFileOriginName(file.getOriginalFilename());
//        fileDto.setFileSaveName(imageUrl.substring(imageUrl.lastIndexOf('/') + 1));
//        fileDto.setFilePath(imageUrl);
//        boardmapper.insertFile(fileDto);
//        return imageUrl;
//    }
	
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
	
	public void exeBoardDelete(Long boardno) {
		System.out.println("BoardService.exeBoardDelete()");

	    // 게시물에 관련된 파일들 삭제
	    List<Integer> fileNos = boardmapper.getFileByBoardNo(boardno);
	    boardmapper.deleteBoardFile(boardno);
	    for (Integer fileNo : fileNos) {
	        boardmapper.deleteFile(fileNo);
	    }
	    

	    // 게시물에 관련된 댓글들 삭제
	    List<Integer> commentNos = boardmapper.getCommentIdsByBoardNo(boardno);
	    boardmapper.deleteBoardComments(boardno);
	    for (Integer commentNo : commentNos) {
	        boardmapper.deleteComment(commentNo);
	    }
	    

	    // 게시물 자체 삭제
	    boardmapper.deleteBoard(boardno);

	}
	
	public int exeBoardViewUp(BoardDto boardDto) {
		System.out.println("BoardService.exeBoardViewUp()");
		
		
		return boardmapper.boardviewup(boardDto);
	}
	
	public List<BoardListDto> exeMainList(int categoryno,int size) {

        return boardmapper.mainList(categoryno,size);
    }
	
	public int getTotalboardMain(int categoryno) {
        return boardmapper.countMainList(categoryno);
    }
}
