package com.example.myproject.repository;

import com.example.myproject.entity.Heart;
import org.apache.ibatis.annotations.Delete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByBoardIdAndUserId(Long boardId, Long userId);// 접속 유저가 보고있는 게시물에 하트를 눌렀는지
    Long countByBoardId(Long boardId);
    void deleteByBoardIdAndUserId(Long boardId, Long userId);
}
