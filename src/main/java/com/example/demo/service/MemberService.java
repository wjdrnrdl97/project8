package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;

public interface MemberService {
	// 회원 목록 조회
	Page<MemberDTO> getList(int pageNumber);	
	// 엔티티를 dto로 변환
	/*
	 * 자바8부터 default를 통해 인터페이스에서 일반메소드를 가질 수 있다.
	 * db는 데이터를 엔티티 클래스로 저장하기 때문에 컨트롤러에서 클라이언트가 등록한 데이터를 dto에서 엔티티클래스로 변환하여 저장한다.
	 * */
	default MemberDTO entityToDto(Member entity) {
		MemberDTO dto = MemberDTO.builder()
						.id(entity.getId())
						.password(entity.getPassword())
						.name(entity.getName())
						.modDate(entity.getModDate())
						.regDate(entity.getRegDate())
						.build();
		return dto;
	}
	
	default Member dtoToEntity(MemberDTO dto) {
		Member entity = Member.builder()
						.id(dto.getId())
						.password(dto.getPassword())
						.name(dto.getName())
						.build();
		return entity;
	}
	
	MemberDTO read(String id);
	
	boolean register(MemberDTO dto);
	

}
