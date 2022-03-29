package com.example.myproject.config.auth;

import com.example.myproject.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 일반유저, OAuth2유저 둘다 담는 객체
 */
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private User user; //콤포지션: 상속의 문제피하기 위해 private 필드로 기존 클래스의 인스턴스를 참조하게함
    private Map<String, Object> attributes;

    public PrincipalDetails(User user){
        this.user = user;
    }
    public PrincipalDetails(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override  //해당 유저의 권한을 리턴함
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }// ROLE_USER
        });
        return collect;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {return this.attributes;}

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    /**
     * 이 아랫 부분 싹 true로 해야함 안그럼 로그인 안됨
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
