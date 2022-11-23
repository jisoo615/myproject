package com.example.myproject.controller;

import com.example.myproject.dto.JoinFormDto;
import com.example.myproject.entity.User;
import com.example.myproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller // RestController 아님
@RequestMapping("/")
public class AccountController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepo;

    /**
     * 회원가입 페이지 로딩
     */
    @GetMapping("joinForm")
    public String joinForm(Model model){
        model.addAttribute("joinFormDto", new JoinFormDto());
        return "account/joinForm";
    }

    /**
     * 회원가입 진행
     */
    @PostMapping("join")
    public String join(JoinFormDto joinFormDto){// <input> 에서 name으로 변수명 같게 해줘야함
        log.info("joinForm: {}", joinFormDto);
        System.out.println("회원가입 1");
        if(joinFormDto.getEmail().isBlank()) return "redirect:joinForm";
        if(joinFormDto.getUsername().isBlank()) return "redirect:joinForm";
        if(joinFormDto.getPassword().isBlank()) return "redirect:joinForm";
        if(userRepo.findByUsername(joinFormDto.getUsername())!=null){// 기존회원이면 에러
            return "redirect:joinForm";
        }
        System.out.println(joinFormDto.getPassword());
        String encodedPwd = bCryptPasswordEncoder.encode(joinFormDto.getPassword());
        System.out.println(encodedPwd);
        User entity = User.builder()
                .username(joinFormDto.getUsername())
                .password(encodedPwd)
                .role("ROLE_USER")
                .email(joinFormDto.getEmail())
                .build();
        System.out.println("회원가입 2");
        userRepo.save(entity);
        System.out.println("회원가입 3");
        return "redirect:loginForm";
    }


}
