package com.example.myproject.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    Long boardId;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String content;
    String writer;
    Long parentId;// 맨 처음 댓글의 id, 자신이 맨 처음이면 id=parentId 임
    char deletedYN;

    @DateTimeFormat
    LocalDateTime createdDate;
    @DateTimeFormat
    LocalDateTime modifiedDate;

    @PrePersist
    void preCreate(){
        this.createdDate = LocalDateTime.now();
        this.deletedYN = 'N';
    }

    @PostUpdate
    void postCreate(){
        this.modifiedDate = LocalDateTime.now();
    }

    public void update(String content){
        this.content = content;
    }

    public void delete(){
        this.deletedYN = 'Y';
    }

    @Builder
    public Comment(Long boardId, Long parentID, String writer, String content){
        this.boardId = boardId; this.parentId = parentID; this.writer = writer; this.content = content;
    }



}
