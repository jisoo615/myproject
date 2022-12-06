package com.example.myproject.repository;

import com.example.myproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByboardIdOrderByParentIdAscIdAsc(@Param("boardId") Long boardId);

    Comment save(Comment comment);

    Optional<Comment> findCommentById(Long id);
}
