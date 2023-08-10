package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;

@SpringBootTest
public class BoardRepositoryTest {

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	// 없는 id로 게시물을 생성하려고 하면 오류가 뜸 외래키로 설정되어있기 때문에
	public void 데이터생성2() {
		Member member = Member.builder().id("user1").build();
		boardRepository.save(new Board().builder().title("1번글").content("내용").writer(member).build());
	}		
	@Test
	public void 데이터생성() {
		//ArgsConstructor적용
		Member member = new Member();
		Optional<Member> result = memberRepository.findById("test1");
		if (result.isPresent()) {
			member = result.get();
		}
		Board board1 = Board.builder().title("1번글").content("내용").viewCount(0).writer(member).build();
		boardRepository.save(board1);
		
		
		Board board2 = Board.builder().title("1번글").content("내용").viewCount(0).writer(member).build();
		boardRepository.save(board2);
		
		Board board3 = Board.builder().title("1번글").content("내용").viewCount(0).writer(member).build();
		boardRepository.save(board3);	
	/* 
	 * 생성자보다는 builder를 사용함에 따라 객체명를 작성을 하기에 가독성이 좋아지고
	 * 필요한 부분만 작성하기에 별 문제도 없다.
	 * 생성패턴 중 빌더패턴 참고
	 * */
	}
	@Test
	public void 데이터단건조회() {
		//Optional<객체> result = repository.findByID(인덱스);
		Optional<Board> result = boardRepository.findById(6);
		// 결과가 존재하면
		if(result.isPresent()) {
			Board board = result.get();
			// 데이터 저장 후 출력
			System.out.println(board);
		}
	}
	@Test
	public void 데이터전체조회() {
		// 데이터갯수를 모르기에 리스트로 생성
		// List<객체>list = repository.findAll();
		List<Board> list = boardRepository.findAll();
		for(Board board : list) {
			System.out.println(board);
		}
	}
	@Test
	public void 게시물수정() {
		Optional<Board> result = boardRepository.findById(1);
		Board board = result.get();
		board.setContent("Tlqkf");
		boardRepository.save(board);
		// 데이터를 수정하면 @EntityListeners에 의해 변화를 감지하고 수정일자가 자동으로 저장하기에 시간이수정됨
	}

	@Test
	public void 데이터삭제() {
		Optional<Board> result = boardRepository.findById(11);
		if(result.isPresent()) {
			boardRepository.deleteById(11);
		}		
	}
	// 파일이 존재하지 않는지 확인하지않고 없는데이터를 가지고 삭제를 한다고하면
	// 서버에서는 에러가 뜨는거기 때문에 방어코드를 짜는것이 좋다. 
	@Test
	public void 전체() {
		boardRepository.deleteAll();
	}
}
