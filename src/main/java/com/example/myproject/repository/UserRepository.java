package com.example.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myproject.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByUsername(String username);
}
