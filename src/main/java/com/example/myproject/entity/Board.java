package com.example.myproject.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.myproject.entity.enums.LockLevel;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user = new User();
	private String writer;
	private String title;
	private String content;
	private int views; //뷰 수
	//외부에선 enum으로 관리, 엔터티에선 String으로 관리 : db에 int로 저장하려면 mybatis handler 구현해야함
	private String lockLevel;
	private char deleteYn; //삭제여부
	@DateTimeFormat
	private LocalDateTime createdDate;//생성일
	@DateTimeFormat
	private LocalDateTime modifiedDate;//수정일
	private Long heart;// 좋아요 수

	@PrePersist
	public void preCreate() {// insert 전에
		this.createdDate = LocalDateTime.now();
		this.deleteYn = 'N';
		this.views = 0;
		this.heart = Long.valueOf(0);
	}
	@PreUpdate
	public void preUpdate() {// update 전에
		this.modifiedDate = LocalDateTime.now();
	}
	
	@Builder
	public Board(String writer, String title, String content, LockLevel lockLevel,
			int views, char deleteYn, User user) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.lockLevel = lockLevel.getKey();
		this.views = views;
		this.deleteYn = deleteYn;
		this.user = user;
	}
	
	//사용자가 수정시 변경가능한 부분만
	public void update(String writer, String title, String content, LockLevel lockLevel) {
		this.writer = writer;//일단 작성자는 맘대로 정할수 있다고 치기
		this.title = title;
		this.content = content;
		this.lockLevel = lockLevel.getKey();
	}
	
	//조회수 증가 메소드
	public void increaseViews() {
		this.views += 1;
	}
	//글 삭제 메소드
	public void delete() {
		this.deleteYn = 'Y';
	}
	//좋아요 증가/감소 메소드
	public Long increaseHearts(){this.heart += 1; return this.heart;}
	public Long decreaseHearts(){this.heart += 1; return this.heart;}

}
