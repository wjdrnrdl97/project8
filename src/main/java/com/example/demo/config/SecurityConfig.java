package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // config 클래스임을 명시
@EnableWebSecurity  //Spring Security 설정클래스를 명시
public class SecurityConfig {
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		// 메뉴별 접근제한 설정		
		http.authorizeHttpRequests()
			.requestMatchers("/register").permitAll()
			.requestMatchers("/assets","/css/*","/js/*").permitAll()
			//브라우저가 recource를 링크를 통해 받기 때문에 recource도 접근을 지정해줘야한다
			.requestMatchers("/").authenticated()  //메인화면은 로그인한 사용자이면 접근 가능
			.requestMatchers("/board/*").hasAnyRole("ADMIN","USER")
			.requestMatchers("/member/*").hasRole("ADMIN");
		
//		http.formLogin();   //시큐리티가 제공하는 기본 로그인페이지 사용하기
		
//		커스텀 로그인 페이지 적용
		http.formLogin()
			.loginPage("/customlogin")
			.loginProcessingUrl("/login")
			.permitAll();
		
		http.csrf().disable();  //csrf : get을 제외하고 상태값을 위조(변경)할 수있는 post,put,delete 메소드를 막음
		
		http.logout();
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		// BCrypt 해싱 함수(BCrypt hashing function)를 사용해서 비밀번호를 인코딩해주는 메서드와 사용자의 의해 제출된 비밀번호와 저장소에 저장되어 있는 비밀번호의 일치 여부를 확인해주는 메서드를 제공합니다.
		// 암호화한 엔진이 같아야함
	}
}
