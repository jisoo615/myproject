package com.example.myproject.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String email;
	private String role;//ROLE_USER, ROLE_BUSINESS, ROLE_ADMIN, ROLE_MANNAGER
	@DateTimeFormat
	private LocalDateTime createDate;
	@DateTimeFormat
	private LocalDateTime modifiedDate;//수정일
	
	private String provider;
	private String providerId;
	
	@PrePersist
	public void preCreate() {
		this.createDate = LocalDateTime.now();
	}
	@PreUpdate
	public void preUpdate() {
		this.modifiedDate = LocalDateTime.now();
	}
	
	
	@Builder
	public User(String username, String password, String email, 
			String role, String provider, String providerId) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
	}
}
