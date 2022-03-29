package com.example.myproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.dto.BoardRequestDto;
import com.example.myproject.dto.BoardResponseDto;
import com.example.myproject.paging.CommonParams;
import com.example.myproject.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ApiController {
	final private BoardService boardService;
		/**
		 * 게시글 리스트 조회
		 */
		@GetMapping("/boards")
		public Map<String, Object> findAll(final CommonParams params) {
		    return boardService.findAll(params);
		}
		// 조회
		@GetMapping(path="board/{id}")
		public BoardResponseDto listById(@PathVariable Long id){
			var dto = boardService.findById(id);
			log.info("id로 조회하는 api 요청응답:{}", dto);
			return dto;
		}
		//글 생성 create
		@PostMapping("board")
		public Long create(@RequestBody BoardRequestDto boardReq) {
			log.info("글 생성 받은 데이터: {}, {}", boardReq.getTitle(), boardReq.getContent());
			return boardService.save(boardReq).getId();
		}
		//수정 update
		@PatchMapping("board/{id}")
		public Long create(@PathVariable("id") Long id, 
							@RequestBody BoardRequestDto boardReq) {
			boardService.update(id, boardReq);
			return id;
		}
		// 삭제
		@DeleteMapping("board/{id}")
		public Long delete(@PathVariable("id") Long id){
			boardService.delete(id);
			return id;
		}

}
