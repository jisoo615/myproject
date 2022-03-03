package com.example.myproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.CustomSQLExceptionTranslatorRegistrar;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myproject.dto.BoardRequestDto;
import com.example.myproject.dto.BoardResponseDto;
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
	
	//최신글 위로가게 전체 조회
	public List<BoardResponseDto> findAll() {
		Sort sort = Sort.by(Direction.DESC, "createdDate");
		List<Board> list = boardRepository.findAll(sort);
		return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
	}

	//id로 조회
	public BoardResponseDto findById(Long id) {
		var entity = boardRepository.findById(id).orElseThrow();
		return new BoardResponseDto(entity);
	}
	//작성자 이름으로 조회
	public List<BoardResponseDto> findByWriter(String writer) {
		var response = boardRepository.findByWriter(writer);
		return response.stream().map(BoardResponseDto::new).collect(Collectors.toList());
		//jpa는 list형 반환시, 조회 안되면 사이즈0인 list반환함
	}
	
	@Transactional
	public Board save(BoardRequestDto req) {//생성저장
		var board = Board.builder()
				.content(req.getContent())
				.lockLevel(req.getLockLevel())
				.title(req.getTitle())
				.writer(req.getWriter())
				.views(0)
				.build();
		log.info("저장될 글{}", board);
		return boardRepository.save(board);
	}
	
	@Transactional
	public Board update(Long id, BoardRequestDto req) {//수정
		Board board = boardRepository.findById(id).orElseThrow();
		board.update(req.getWriter(),req.getTitle(),req.getContent(),req.getLockLevel());
		return board;
	}

	@Transactional
	public void delete(Long id) {
		boardRepository.deleteById(id);
	}
	
	

}
