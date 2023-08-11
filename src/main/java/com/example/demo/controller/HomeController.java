package com.example.demo.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
//	@GetMapping("/")
//	public String home() {
//		return "/home/main";  // localhost:8080
//	}
	@GetMapping("/")
	public String home(Principal principal,Model model) {
		model.addAttribute("id",principal.getName());
		return "/home/main";  // localhost:8080
	}
	
	//커스텀 로그인 페이지 반환하는 메소드
	@GetMapping("/customlogin")
	public String customLogin() {
		return "home/login";
	}
}
