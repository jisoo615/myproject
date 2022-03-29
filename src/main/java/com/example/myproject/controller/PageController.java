package com.example.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
	@GetMapping({"", "/"})
	public String index(){
		return "project/index";
	}
	
	@GetMapping("project/home")
	public String home() {
		return "project/home";
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
	@GetMapping("/loginForm")
	public String loginForm() {
		return "account/loginForm";
	}
	@GetMapping("/joinForm")
	public String joinForm() {
		return "account/joinForm";
	}

}
