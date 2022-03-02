package com.example.myproject.dto;

import com.example.myproject.entity.Board;
import com.example.myproject.entity.enums.LockLevel;

import lombok.Data;

@Data
public class BoardRequestDto {

	Long id;
	String writer;
	String title;
	String content;
	LockLevel lockLevel;
	
	public Board toBoardEntity() {//요청 -> 엔터티
		return Board.builder()
				.content(content)
				.title(title)
				.writer(writer)
				.lockLevel(lockLevel)
				.build();
	}
}
