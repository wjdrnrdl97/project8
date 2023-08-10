package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 변화를 감지하는 리스너 사용을 위해 어노테이션 선언
@SpringBootApplication
public class RealProject06Application {

	public static void main(String[] args) {
		SpringApplication.run(RealProject06Application.class, args);
	}

}
