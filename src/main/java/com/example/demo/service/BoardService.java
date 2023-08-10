	package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;

public interface BoardService {
	
	// 게시물을 등록
	int register(BoardDTO dto);
	
	// dto를 엔티티로 변환하는 메소드
	/*
	 * 자바8부터 default를 통해 인터페이스에서 일반메소드를 가질 수 있다.
	 * db는 데이터를 엔티티 클래스로 저장하기 때문에 컨트롤러에서 클라이언트가 등록한 데이터를 dto에서 엔티티클래스로 변환하여 저장한다.
	 * */
	// 작성자 필드 수정
	default Board dtoToEntity(BoardDTO dto) {
		Member member = Member.builder().id(dto.getWriter()).build(); // 작성자 객체 생성
		Board entity = Board.builder()
				.no(dto.getNo())
				.title(dto.getTitle())
				.content(dto.getContent())
				.viewCount(dto.getViewCount())
				.writer(member)
				.build();
		return entity;
	}
	
	// 방명록 목록조회	
	// 목록조회(페이징) 
	/*
	 *   List<BoardDTO> getList();  => 변경
	 * */
	Page<BoardDTO> getList(int pageNumber);
	
	
	// 엔티티를 dto로 변환하는 메소드
	/*
	 * 컨트롤러는 데이터를 dto로 전송받기 때문에 db에 저장된 엔티티클래스를 dto클래스로 변환 후 가져온다. 
	 * */
	default BoardDTO entityToDto(Board entity) {
		BoardDTO dto = BoardDTO.builder()
				.no(entity.getNo())
				.title(entity.getTitle())
				.content(entity.getContent())
				.writer(entity.getWriter().getId())
				// writer는 String이고 entity.getWriter는 Member이기 때문에 타입이 맞지않음.
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.viewCount(entity.getViewCount())
				.build();
		
		return dto;
	}
	
	// 게시물 상세조회
	/*
	 * 컨트롤에서 글번호를 전달받아 게시물 한건을 반환한다.
	 * */
	BoardDTO read(Integer no);
	
//	void getView(BoardDTO boardDTO);
	
	// 게시물 수정메소드
	void modify(BoardDTO boardDTO);
	
	// 게시물 삭제
	int remove(int no);	
}
