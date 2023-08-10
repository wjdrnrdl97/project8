package com.example.demo.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;

@Service  // 비즈니스 로직을 처리하는 역할을 명시
/*
 * 사용하고싶은 로직에 @service만 떼어다가 붙엿다가
 * 같이 사용할 수 없음
 * 만일 같이 사용하고싶으면 PRIMARY 어노테이션을 사용해 우선순위를 메김
 * */
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardRepository boardRepository;  // 사용할 리파지토리를 멤버로 선언

	@Override
	public int register(BoardDTO dto) {  
		System.out.println(dto);  // dto의 정보 출력
		
		Board entity = dtoToEntity(dto);  // 컨트롤러에서 전달받은 dto를 엔터티로 변환
		boardRepository.save(entity);  // 리파지토리에 엔티티를 전달하여 저장
		System.out.println(entity);  // dto를 엔터티로 변환한 후 결과값 출력
		
		return entity.getNo(); // 새로 등록된 게시물번호를 반환
	}

	@Override
	public Page<BoardDTO> getList(int page) {  //페이지 번호 받기
		int pageNum = (page==0) ? 0 : page -1;   // page는 index 처럼 0부터 시작.
		Pageable pageable = PageRequest.of(pageNum, 10, Sort.by("no").descending());		
		Page<Board> entityPage = boardRepository.findAll(pageable);
		//게시물 목록을 페이지에 담아서 조회하기 -> 오버로딩함
		Page<BoardDTO> dtopage = entityPage.map(entity -> entityToDto(entity));  //엔티티 타입의 페이지를 DTO 타입으로 변환
		
		return dtopage;
	}	


	@Override
	public BoardDTO read(Integer no) {		
			Optional<Board> result = boardRepository.findById(no);
			if(result.isPresent()) {
				Board board = result.get();
				board.setViewCount(board.getViewCount()+1);
				boardRepository.save(board);
				return entityToDto(board);
			}else {
				return null;	
			}		
		}
	
//	@Override	
//	public BoardDTO read(int no) {		
//		Optional<Board> result = boardRepository.findById(no);
//		if(result.isPresent()) {
//			Board board = result.get();
//			return entityToDto(board);
//		}else {
//			return null;	
//		}		
//	}	

//	@Override
//	public void getView(BoardDTO boardDTO) {		
//		Optional<Board> result = boardRepository.findById(boardDTO.getNo());
//		if(result.isPresent()) {
//			Board board = result.get();
//			board.setViewCount(board.getViewCount()+1);
//			boardRepository.save(board);
//		}
//	}	

	@Override
	public void modify(BoardDTO boardDTO) {
		
		// 변경가능한 객체 = 제목, 내용
		Optional<Board> result = boardRepository.findById(boardDTO.getNo());
		
		/*
		 * 화면에서 입력하여 통해 데이터를 수정되는 것이 아닌 외적인 방법을 통해 임의적으로 데이터가 수정되는 것을
		 * 막고자 엔터리형식으로 사용하고 getter/setter 메서드를 통해 변경할 수 있는 주제, 내용만 변경
		 * */
		
		if(result.isPresent()) {
			Board entity = result.get();
 	
			entity.setTitle(boardDTO.getTitle());
			entity.setContent(boardDTO.getContent());			
			boardRepository.save(entity);
		}
		
		/*
		 * 이 방식대로 사용하게 되면 수정되면 안되는 등록일자나 작성자 등이 수정된다.
		 * getter/setter 메소드로 데이터를 보호함과 동시에 값을 변경할 수 있다. 캡슐화
		 * */
//		Board entity = dtoToEntity(boardDTO);
//		boardRepository.save(entity);

	}

	@Override
	public int remove(int no) {
		Optional<Board> result = boardRepository.findById(no);
		if(result.isPresent()) {
			boardRepository.deleteById(no);
			return 1;
		}else {
			return 0;
		}
		
	}


}
