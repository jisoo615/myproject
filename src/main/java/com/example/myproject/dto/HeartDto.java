package com.example.myproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HeartDto {// 해당 게시글에, 접속자의 하트여부(id/-1)/ 게시글 전체 하트수
    private Long boardId;
    private Long userId;// userId -> username 변경 : 프론트는 userId 모름
    private Long total;
    private boolean isFull;

    @Builder
    public HeartDto(Long boardId, Long userId, Long total, boolean isFull){
        this.boardId = boardId;
        this.userId = userId;
        this.total = total;
        this.isFull = isFull;
    }
}
