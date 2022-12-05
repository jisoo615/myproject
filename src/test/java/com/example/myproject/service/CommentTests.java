package com.example.myproject.service;

import com.example.myproject.dto.CommentRequestDto;
import com.example.myproject.entity.Comment;
import com.example.myproject.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentTests {
    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;

    CommentRequestDto createReqDto(Long parentId){
        return CommentRequestDto.builder()
                .boardId(1L).content("helloooo").writer("me")
                .parentId(parentId)
                .build();
    }

    @Test
    void saveComment(){
        commentService.save(createReqDto( 1L));// parent댓글이 2임
        commentService.save(createReqDto(2L));
        commentService.save(createReqDto( 1L));
        commentService.save(createReqDto( 2L));

        Comment entity = commentRepository.findCommentById(3L).get();
        Assertions.assertEquals(entity.getId(), 3L);
        Assertions.assertEquals(entity.getParentId(), 1L);
        Assertions.assertEquals(entity.getBoardId(), 1L);
        Assertions.assertEquals(entity.getDeletedYN(), 'N');

    }
    @Test
    void deleteComment(){
        saveComment();

        Comment deleetedEntity = commentService.deleteComment(3L);
        Assertions.assertEquals(deleetedEntity.getDeletedYN(), 'Y');
    }
    @Test
    void findAll(){
        deleteComment();

        List<Comment> comments = commentRepository.findAllByboardIdOrderByParentIdAscIdAsc(1L);
        for(Comment c : comments){
            if(c.getDeletedYN()=='Y') Assertions.assertEquals(c.getDeletedYN(), 'Y');
            else Assertions.assertEquals(c.getDeletedYN(), 'N');
        }
    }

    @Test
    void updateComment(){
        commentService.save(createReqDto(1L));
        commentService.update(CommentRequestDto.builder()
                .boardId(1L).content("edited").writer("me")
                        .id(1L).parentId(1L)
                .build());
        Comment entity = commentRepository.findCommentById(1L).get();
        Assertions.assertEquals(entity.getContent(), "edited");
        Assertions.assertEquals(entity.getId(), 1L);
    }

}
