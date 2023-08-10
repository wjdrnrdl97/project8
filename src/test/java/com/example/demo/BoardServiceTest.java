package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;

@SpringBootTest
public class BoardServiceTest {
	
	@Autowired
	BoardService boardService;

	// 데이터생성
//	@Test
//	public void test1() {
////		BoardDTO dto = new BoardDTO(0,"1번글","내용입니다","둘리",null,null);
//		BoardDTO dto = new BoardDTO(0,"2번글","내용입니다","또치",null,null);
//		int no = boardService.register(dto);
//		System.out.println("새로운 게시물 번호: " + no);
//	}
	
	// 목록조회
//	@Test
//	public void test2() {
//		List<BoardDTO>list = boardService.getList();
//		// 향상된 for문
//		for(BoardDTO dto : list) {
//			System.out.println(dto);
//		}		
//	}
	
	// 단건조회
	@Test
	public void test3() {
		BoardDTO result = boardService.read(8);
		System.out.println(result);		
	}
	
	@Test
	public void test4() {
		BoardDTO dto = boardService.read(1);		
		dto.setContent("내용이수정되었습니다~");
		boardService.modify(dto);
	}
	
	@Test
	public void test5() {
		if(boardService.remove(5)==1) {
			System.out.println("삭제 성공");
		}else if(boardService.remove(5)==0){
			System.out.println("삭제 실패");
		}		
	}
	
	@Test
	public void test6() {
		for(int i = 0; i<=30;i++) {
			boardService.register(new BoardDTO().builder().no(i).title("제목").content("내용").writer("둘리").viewCount(0).build());
		}
	}
	@Test
	public void test7() {		
		Page<BoardDTO> page = boardService.getList(4);
		List<BoardDTO> result = page.getContent();
		for(BoardDTO dto : result) {
			System.out.println(dto);
		}
	}
}
