package com.example.myproject.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.myproject.entity.enums.LockLevel;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String writer;
	private String title;
	private String content;
	private int views; //뷰 수
	private LockLevel lockLevel;
	private char deleteYN; //삭제여부
	@DateTimeFormat
	private LocalDateTime createdDate;//생성일
	@DateTimeFormat
	private LocalDateTime modifiedDate;//수정일
	
	@PrePersist
	public void preCreate() {
		this.createdDate = LocalDateTime.now();
	}
	@PreUpdate
	public void preUpdate() {
		this.modifiedDate = LocalDateTime.now();
	}
	@PostRemove
	public void postRemove() {
		this.deleteYN = 'Y';
	}
	
	@Builder
	public Board(String writer, String title, String content, LockLevel lockLevel,
			int views, char deleteYN) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.lockLevel = lockLevel;
		this.views = views;
		this.deleteYN = deleteYN;
	}

}
