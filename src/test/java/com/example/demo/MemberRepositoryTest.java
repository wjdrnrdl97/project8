package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Member;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;

@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	BoardRepository boardRepository;

	@Test
	public void select() {
		Optional<Member> result = memberRepository.findById("test1");
		if (result.isPresent()) {
			Member member = result.get();
			System.out.println(member);
		} else {
			System.out.println(-9999);
		}
	}

	@Test
	public void selectAll() {
		List<Member> list = memberRepository.findAll();
		for (Member member : list) {
			System.out.println(member);
		}
	}
	@Test
	public void delete() {
		Optional<Member> result = memberRepository.findById("user1");
		if (result.isPresent()) {
			memberRepository.deleteById("user1");
		}
	}

	@Test
	public void deleteAll() {
		memberRepository.deleteAll();
		/*
		 * 회원과 게시물은 N:1(부모 : 회원, 자식 게시물)이고 회원의 id를 외래키로 설정되어있기 때문에 회원과 연결되어있는 게시물을 먼저 삭제
		 * 후 회원을 삭제해야한다.
		 */
	}
	@Test
	public void 대량생산() {
		List<Member> list = new ArrayList<>();
		for(int i=0; i<=30;i++) {
			list.add(new Member("testUser"+i, "1111", "테스트용","test"));
		}
		memberRepository.saveAll(list);
	}
	@Test
	public void 생성() {
		Member member = Member.builder()
						.id("test1")
						.password("1234")
						.name("둘리")
						.build();
		memberRepository.save(member);
	}
}
