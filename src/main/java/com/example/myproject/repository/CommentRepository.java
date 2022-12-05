package com.example.myproject.repository;

import com.example.myproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByboardIdOrderByParentIdAscIdAsc(Long boardId);

    Comment save(Comment comment);

    Optional<Comment> findCommentById(Long id);
}
