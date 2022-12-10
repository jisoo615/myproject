package com.example.myproject.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(value= PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
@Data
public class CommentRequestDto {// customer->server
    Long boardId;
    Long id;
    String content;
    String writer;
    Long parentId;// 댓글중에 제일 제일 윗부분의 댓글 id

    @Builder
    public CommentRequestDto(Long boardId, Long id, String content, String writer, Long parentId){
        this.boardId = boardId; this.content = content; this.id = id; this.writer = writer; this.parentId = parentId;
    }

    }
