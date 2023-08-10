package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;

/*
 * 회원정보 관련 내용은 관리자만이 관리되어야 하기때문에 URL경로 수정
 * 접근제한이 필요한 페이지는 URL변경을 통해 접근제한을 설정한다.
 * */
@Controller // 스프링이 어노테이션을 통해 컨트롤러임을 확인
//@RequestMapping("/member")  // 시큐리티를 위해 상세경로 제거 후 상세경로가 필요한 맵핑만 추가
public class MemberController {

	@Autowired
	MemberService memberService;

	@GetMapping("/member/list")
	public void list(@RequestParam(defaultValue="0")int page, Model model) {
		Page<MemberDTO> list = memberService.getList(page);
		model.addAttribute("list", list);
	}
	@GetMapping("/register")
	public String register() {
		return "member/register";  // 상세경로를 제거하였기때문에 html 경로 수정
	}
	@PostMapping("/register")
	public String postRegister(MemberDTO dto, RedirectAttributes attributes) {
		boolean isSuccess = memberService.register(dto);
		if(isSuccess) {
			attributes.addFlashAttribute("msg", "정상적으로 회원등록이 되었습니다.");
			return "redirect:/member/list";  // 등록성공시 목록화면으로 가기
		}else {
			attributes.addFlashAttribute("msg", "아이디가 중복되어 등록에 실패하였습니다.");
			// 알림창 중복을 막기위해 addFlashAttribute 사용(일회성 데이터 전달 목적)
			return "redirect:/member/register";  // 등록실패시 회원가입폼으로 돌아가기
		}	
	}
	@GetMapping("/member/read")
	public void read(String id,@RequestParam(defaultValue = "0") int page, Model model) {
		MemberDTO dto = memberService.read(id);
		model.addAttribute("dto",dto);
		model.addAttribute("page",page);
	}
}
