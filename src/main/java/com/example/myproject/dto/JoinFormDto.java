package com.example.myproject.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JoinFormDto {
    private String username;
    private String password;
    private String email;
}
