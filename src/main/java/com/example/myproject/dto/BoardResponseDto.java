package com.example.myproject.dto;

import java.time.LocalDateTime;

import javax.persistence.Id;

import com.example.myproject.entity.Board;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardResponseDto {
	@Id
	private Long id;
    private String title; 
    private String content;
    private String writer;
    @JsonProperty("lock_level")
    private String lockLevel;
    private int views;
    private char deleteYn; // 삭제 여부 'N' 'Y'
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long heart;
        
    public BoardResponseDto(Board entity) {// 엔터티 -> 응답
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
        this.lockLevel = entity.getLockLevel();
        this.views = entity.getViews();
        this.deleteYn = entity.getDeleteYn();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.heart = entity.getHeart();
    }
}
