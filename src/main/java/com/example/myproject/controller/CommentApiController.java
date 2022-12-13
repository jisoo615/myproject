package com.example.myproject.controller;

import com.example.myproject.config.auth.PrincipalDetails;
import com.example.myproject.dto.CommentRequestDto;
import com.example.myproject.entity.Comment;
import com.example.myproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController// = @Controller + @ResponseBpdy
@RequiredArgsConstructor
@RequestMapping("api/comment")
public class CommentApiController {
    final private CommentService commentService;

    @GetMapping("{board_id}")
    public List<Comment> findAll(@PathVariable("board_id") Long boardId){
        log.info(boardId+"번 게시물 댓글 조회 api");
        return commentService.findAll(boardId);
    }
    //댓글 작성
    @PostMapping("")
    public Long create(@RequestBody CommentRequestDto dto,
                       @AuthenticationPrincipal PrincipalDetails principalDetails){
        // 댓글쓰면 보던 글id 리턴
        log.info("댓글 작성 dto={}", dto );
        dto.setWriter(principalDetails.getUsername());
        Long boardId = commentService.save(dto).getBoardId();
        return boardId;
    }
    //댓글 수정
    @PatchMapping("")
    public Long update(@RequestBody CommentRequestDto dto){
        // 댓글쓰면 보던 글id 리턴
        Long boardId = commentService.update(dto).getBoardId();
        return boardId;
    }
    // 댓글 삭제
    @DeleteMapping("{id}")
    public Long deleteComment(@PathVariable Long id){
        Long boardId = commentService.deleteComment(id).getBoardId();
        return boardId;
    }

}
