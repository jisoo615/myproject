package com.example.myproject.controller;

import com.example.myproject.dto.JoinFormDto;
import com.example.myproject.entity.User;
import com.example.myproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AccountController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepo;

    /**
     * 회원가입 진행
     */
    @PostMapping("join")
    public String join(JoinFormDto form){// <input> 에서 name으로 변수명 같게 해줘야함
        log.info("회원가입 1");
        if(userRepo.findByUsername(form.getUsername())!=null){// 기존회원이면 에러
            return "redirect:joinForm";
        }
        System.out.println(form.getPassword());
        String encodedPwd = bCryptPasswordEncoder.encode(form.getPassword());
        System.out.println(encodedPwd);
        User user = User.builder()
                .username(form.getUsername())
                .password(encodedPwd)
                .role("ROLE_USER")
                .email(form.getEmail())
                .build();
        log.info("회원가입 2");
        userRepo.save(user);
        log.info("회원가입 3");
        return "redirect:loginForm";
    }


}
