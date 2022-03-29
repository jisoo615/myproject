package com.example.myproject.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Getter;

@NoArgsConstructor
@Getter
@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String username;
	//outh2 로그인시 password=null -> nullable=ture 임
	@Column(length = 100)
	private String password;
	@Column(nullable = false)
	private String email;
	private String role;//ROLE_USER, ROLE_BUSINESS, ROLE_ADMIN, ROLE_MANNAGER
	@DateTimeFormat
	private LocalDateTime createDate;
	@DateTimeFormat
	private LocalDateTime modifiedDate;//수정일
	
	private String provider;  // google
	private String providerId;// google_123456789
	
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
