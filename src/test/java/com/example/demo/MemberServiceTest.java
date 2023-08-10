package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;

@SpringBootTest
public class MemberServiceTest {
	
	@Autowired
	MemberService memberService;
	
	@Test
	public void test1() {
		Page<MemberDTO> page = memberService.getList(3);
		List<MemberDTO> list = page.getContent();
		for(MemberDTO dto : list) {
			System.out.println(dto);
		}
	}
	@Test
	public void test2() {
		// 신규회원등록
		MemberDTO dto = MemberDTO.builder()
						.id("testUser2")
						.password("1234")
						.name("둘리")
						.build();
		// 중복회원 조회
		boolean test = memberService.register(dto);
		if(test) {
			System.out.println("회원이 등록되었습니다.");
		}else {
			System.out.println("중복된 회원입니다.");
		}		
	}
	@Test
	public void test3() {
		MemberDTO memberDto = memberService.read("user2");
		System.out.println(memberDto);
	}
	@Test
	public void test4() {
		MemberDTO dto = MemberDTO.builder()
						.id("test3")
						.password("1234")
						.name("둘리")
						.build();
		Boolean test = memberService.register(dto);
		if(test) {
			MemberDTO dto2 = memberService.read("test1");
			System.out.println(dto2);
		}
	}	
}
