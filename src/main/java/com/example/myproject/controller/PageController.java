package com.example.myproject.controller;

import com.example.myproject.dto.JoinFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {
	@GetMapping({"", "/"})
	public String index(){
		return "project/index";
	}
	
	@GetMapping("project/list")
	public String home() {
		return "project/list";
	}
	@GetMapping("project/view/{id}")
	public String view( @PathVariable Long id,
						Model model) {
		model.addAttribute("id", id);// 조회하는 id
		
		return "project/view";
	}
	@GetMapping("project/write")// 최초 생성, 수정
	public String write(@RequestParam(required=false) Long id, Model model) {
		model.addAttribute("id", id);
		return "project/write";
	}
	
	@GetMapping("board/list")
	public String list() {
		return "board/list";
	}

	/**
	 * 로그인 페이지
	 */
	@GetMapping("loginForm")
	public String loginForm() {
		return "account/loginForm";
	}
	// 여기서 로그인시 오류나면 표시

	// 댓글 페이지
	@GetMapping("project/{board_id}/comment")
	public String comment(@PathVariable("board_id") Long boardId, Model model){
		model.addAttribute("board_id", boardId);
		return "project/comment";
	}// 어떤 글의 뎃글인지 알려주기 위해 글id를 모델에 담아 보냄 -> /*[[ ${id} ]]*/ 로 js에서 받을수있음

	//TODO: 마이페이지

}
