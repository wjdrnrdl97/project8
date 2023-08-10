package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	/*
	 * 싱글톤 방식으로 매개변수를 BoardService인터페이스 참조변수로 주입(oci, dip 원칙 적용)
	 * 
	 */
	@Autowired
	private BoardService boardService;

	// 메인화면
	@GetMapping("/main")
	public void main() {

	}

	// 목록화면
	@GetMapping("/list")
	public void list(@RequestParam(defaultValue = "0") int page, Model model) {
		Page<BoardDTO> list = boardService.getList(page);
		model.addAttribute("list", list);
		System.out.println("전체 페이지 수: " + list.getTotalPages());
		System.out.println("전체 게시물 수: " + list.getTotalElements());
		System.out.println("현재 페이지 번호: " + (list.getNumber() + 1));
		System.out.println("페이지에 표시할 게시물 수 : " + list.getNumberOfElements());
		
	}

	// 등록화면
	@GetMapping("/register")
	public void register() {
		System.out.println();
	}

	// 등록처리
	@PostMapping("/register")
	public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
		int no = boardService.register(boardDTO);
		redirectAttributes.addFlashAttribute("msg", no);

		// 등록처리 후 목록화면주소 반환, “redirect:” 는 html경로가 아닌 URL주소를 작성한다.
		return "redirect:/board/list";
	}

	// @RequestParam 생략(int라는 단순타입이기 때문에)
	// read(파라미터 타입 파리미터 매개변수이름 / 데이터타입)
	// url 작성시 read?no = 값 => 주소?파라미터 매개변수이름 = 값
	@GetMapping("/read")
	public void read(int no, @RequestParam(defaultValue = "0") int page, Model model) {
		BoardDTO dto = boardService.read(no);
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);  //페이지 번호 전달
	}
//	@PostMapping("/read")
//	public String getViewPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
//		boardService.getView(boardDTO);		
//		return "redirect:/board/read";
//	}

	@GetMapping("/modify")
	public void modify(int no, Model model) {
		BoardDTO boardDTO = boardService.read(no);
		model.addAttribute("dto", boardDTO);		
	}

	@PostMapping("/modify")
	public String modifyPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
		boardService.modify(boardDTO);
		redirectAttributes.addAttribute("no", boardDTO.getNo());
		// 주소에 파라미터이름과 특정값이 입력됨에 따라 리다이렉션후에 수정했던 페이지번호 그대로 유지가됨
		return "redirect:/board/read";
		// 리다이렉트 반환 : "redirect:/절대경로 반환주소"
	}

	// 수정화면에서 삭제를 진행하기에 별도의 get요청이 필요가 없다
	@PostMapping("/remove")
	public String removePost(int no) {
		boardService.remove(no);
		return "redirect:/board/list";
		// 리다이렉트 반환 : "redirect:/절대경로 반환주소"
	}
}
