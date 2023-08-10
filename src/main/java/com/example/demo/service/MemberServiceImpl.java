package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

@Service  // 스프링이 어노테이션을 통해 서비스임을 확인
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository memberRepository;

	@Override
	public Page<MemberDTO> getList(int page) {
		int pageNum = (page == 0) ? 0 : page - 1;  // page의 index는 0부터

		Pageable pageable = PageRequest.of(pageNum, 10, Sort.by("regDate").descending());
		
		//페이지 자료구조에 회원정보 저장
		Page<Member> entityPage = memberRepository.findAll(pageable);
		// 회원정보를 dto로 변환하여 저장(page의 컬렉션를 import하고 하고있기 때문에 컬렉션의 map메소드 사용 가능)
		Page<MemberDTO> dtoPage = entityPage.map(entity -> entityToDto(entity));
		return dtoPage;
	}

	@Override
	public boolean register(MemberDTO dto) {
		String id = dto.getId();
//		MemberDTO getDto =read(id);
		Optional<Member> getDto = memberRepository.findById(id);
		if(getDto.isPresent()) {
			System.out.println("사용중인 아이디입니다.");
			return false;	
		}
		Member entity = dtoToEntity(dto);
		
		// 패스워드 인코더로 패스워드 암호화하기
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashpassword = passwordEncoder.encode(entity.getPassword());  // 엔티티의 패스워드를 가져와 암호화
		entity.setPassword(hashpassword);  // 암호화된 패스워드로 변경
		memberRepository.save(entity);
		return true;
	}

	@Override
	public MemberDTO read(String id) {
		Optional<Member> result = memberRepository.findById(id);
		if(result.isPresent()) {
			Member member = result.get();
			memberRepository.save(member);
			return entityToDto(member);
		}else {
			return null;
		}
	}
}
