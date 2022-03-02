package com.example.myproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myproject.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

	List<Board> findByWriter(String writer);
	
}
