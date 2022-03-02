package com.example.myproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.myproject.dto.BoardRequestDto;
import com.example.myproject.entity.Board;
import com.example.myproject.repository.BoardRepository;
import com.example.myproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	
	public List<Board> findAll() {
		return boardRepository.findAll();
	}

	public Board findById(Long id) {
		var response = boardRepository.findById(id);
		if(response.isEmpty()) return null;
		return response.get();
	}

	public List<Board> findByWriter(String writer) {
		var response = boardRepository.findByWriter(writer);
		return response;//jpa는 list형 반환시, 조회 안되면 사이즈0인 list반환함
	}

	public Board create(BoardRequestDto req) {
		var board = Board.builder()
				.content(req.getContent())
				.lockLevel(req.getLockLevel())
				.title(req.getTitle())
				.writer(req.getWriter())
				.build();
		log.info("저장될 글{}", board);
		return boardRepository.save(board);
	}
	
	

}
