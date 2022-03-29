package com.example.myproject.controller;

import com.example.myproject.dto.JoinForm;
import com.example.myproject.entity.User;
import com.example.myproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class AccountController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepo;

    /**
     * 회원가입 진행
     */
    @PostMapping("join")
    public String join(JoinForm form){// <input> 에서 name으로 변수명 같게 해줘야함
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
        userRepo.save(user);
        return "redirect:loginForm";
    }


}
