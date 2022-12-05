package com.example.myproject.service;

import com.example.myproject.dto.CommentRequestDto;
import com.example.myproject.entity.Comment;
import com.example.myproject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    final private CommentRepository commentRepository;

    public List<Comment> findAll(Long boardId){
        return commentRepository.findAllByboardIdOrderByParentIdAscIdAsc(boardId);
    }
    @Transactional
    public Comment save(CommentRequestDto dto){
        Comment entity = Comment.builder()
                .writer(dto.getWriter())
                .content(dto.getContent())
                .parentID(dto.getParentId())
                .boardId(dto.getBoardId())
                .build();
        entity = commentRepository.save(entity);
        return entity;
    }
    @Transactional
    public Comment update(CommentRequestDto dto){
        Comment entity = commentRepository.findCommentById(dto.getId()).get();
        entity.update(dto.getContent());
        commentRepository.save(entity);
        return entity;
    }

    public Comment deleteComment(Long id){
        Comment entity = commentRepository.findCommentById(id).get();
        entity.delete();
        return commentRepository.save(entity);
    }

}
