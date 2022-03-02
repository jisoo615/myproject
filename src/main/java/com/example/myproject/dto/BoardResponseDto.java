package com.example.myproject.dto;

import java.time.LocalDateTime;

import com.example.myproject.entity.Board;

public class BoardResponseDto {
	private Long id;
    private String title; 
    private String content;
    private String writer;
    private int views;
    private char deleteYn; // 삭제 여부 'N' 'Y'
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BoardResponseDto(Board entity) {// 엔터티 -> 응답
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
        this.views = entity.getViews();
        this.deleteYn = entity.getDeleteYN();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
