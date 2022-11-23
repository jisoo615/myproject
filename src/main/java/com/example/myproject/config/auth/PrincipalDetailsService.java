package com.example.myproject.config.auth;

import com.example.myproject.entity.User;
import com.example.myproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 일반 로그인, 자동 로그인 (자동 회원가입x)
 */
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {// 로그인시키기
        System.out.println(username);
        User entity = userRepo.findByUsername(username);
        System.out.println("loadUserByUsername: "+entity.getUsername()+","+ entity.getEmail()+","+ entity.getPassword());
        if(entity==null){
            throw new UsernameNotFoundException(username);
            // 로그인 실패시 예외 던지기
        }
        return new PrincipalDetails(entity);
    }

}
