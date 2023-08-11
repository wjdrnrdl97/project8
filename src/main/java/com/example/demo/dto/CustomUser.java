package com.example.demo.dto;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User{
	public CustomUser(MemberDTO dto) {
		super(dto.getId(),dto.getPassword(),Arrays.asList(new SimpleGrantedAuthority(dto.getRole())));
		// id, 패스워드, 권한목록(컬렉션) 호출
	}
}
