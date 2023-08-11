package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomUser;
import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;

//시큐리티에서 제공하는 UserDetailsService 인터페이스를 상속받는다.
/*
 * 사용자 커스텀 로그인 인증 서비스
 * loadUserByUsername를 오버라이드 하여 사용자 정보를 조회하고 인증객체를 생성한다
 * 유저 인증서비스는 provider에 등록한다
 * 패스워드 일치여부는 security가 config에서 설정대로 내부적으로 처리
 * */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MemberService memberService;

	@Override // 사용자가 로그인 요청을 한 순간 security가 해당 메소드를 호출
	public UserDetails loadUserByUsername(String username) {
		MemberDTO dto = memberService.read(username);
		if (dto == null) {
			throw new UsernameNotFoundException("");
		} else {
			return new CustomUser(dto);  // customuser extends user implements userdetails
		}
	}
}
