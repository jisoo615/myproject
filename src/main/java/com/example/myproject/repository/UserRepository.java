package com.example.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myproject.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
