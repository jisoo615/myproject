package com.example.myproject.dto;

import com.example.myproject.entity.Board;
import com.example.myproject.entity.enums.LockLevel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoardRequestDto {

	//private Long id;
	private String writer;
	private String title;
	private String content;
	@JsonProperty("lock_level")
	private LockLevel lockLevel;
	//private int views;
	
	public Board toBoardEntity() {//요청 -> 엔터티
		return Board.builder()
				.content(content)
				.title(title)
				.writer(writer)
				.lockLevel(lockLevel)
				.views(0)
				.build();
	}
	
	@Builder
	public BoardRequestDto(String writer, String title, String content, 
			LockLevel lockLevel) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.lockLevel = lockLevel;
	}
	
	
}
