package com.example.myproject.repository;

import com.example.myproject.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c.id, c.board_id, c.parent_id, c.writer, c.deletedyn, c.created_date, c.modifed_date," +
            " if(c.deletedyn='Y', 'deleted comment', c.content) as content"+
            " from comment c" +
            " where board_id = :board_id" +
            " order by c.parent_id Asc, c.id Asc"
    , nativeQuery = true)
    List<Comment> findAllByboardIdOrderByParentIdAscIdAsc(@Param("board_id") Long boardId);

    Comment save(Comment comment);

    Optional<Comment> findCommentById(Long id);
}
