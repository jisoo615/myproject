package com.example.myproject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.dto.BoardRequestDto;
import com.example.myproject.entity.Board;
import com.example.myproject.service.BoardService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ApiController {
	final private BoardService boardService;
		@GetMapping(path="board/list")
		public List<Board> list(
				@RequestParam(required = false) String writer) {
			//파라미터 없으면 전체 리스트
			if(writer==null) {
				return boardService.findAll();
			}
			//작성자 있으면
			var boards = boardService.findByWriter(writer);
			return boards;
		}
		@GetMapping(path="board/list/{id}")
		public Board listById(@PathVariable Long id){
			var board = boardService.findById(id);
			if(board==null) return null;//bad request
			return board;
		}
		
		@PostMapping("board/create")
		public ResponseEntity<?> create(@RequestBody BoardRequestDto boardReq) {
			log.info("글 생성 저장: {}", boardReq);
			var response = boardService.create(boardReq);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		@PutMapping("board/create/{id}")
		public void create(@PathVariable("id") Long id) {
			//글 수정, 업데이트
		}

}
