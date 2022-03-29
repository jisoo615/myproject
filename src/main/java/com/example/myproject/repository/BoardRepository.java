package com.example.myproject.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myproject.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	/**
	 * 게시글 리스트 조회 - (작성자 게시글만)
	 */
	List<Board> findByWriter(String writer);
	
	 /**
     * 게시글 리스트 조회 - (삭제 여부 기준)
     */
    List<Board> findAllByDeleteYn(final char deleteYn, final Sort sort);
}
