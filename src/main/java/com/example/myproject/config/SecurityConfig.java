package com.example.myproject.config;

import com.example.myproject.config.oauth.PrincipalOAuth2UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)// @Scured,@Pre/PostAuthorize 어노테이션 활성화-컨트롤러에서 부분 권한추가가능
@RequiredArgsConstructor
@Configuration //configuration 에서 AllArgsConstructor가 빠져서 bean등록 오류가 생성됨 -> @AllArgs..추가
@EnableWebSecurity//스프링 시큐리티 필터가 필터체인에 등록이 됨
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	final private PrincipalOAuth2UserService principalOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/",
					"/login","/loginForm",
					"/project/list",
					"/join").permitAll() // 모두 허용
		.antMatchers("/css/**/*", "/scripts/**/*").permitAll()
		.antMatchers("/joinForm").anonymous() // 인증되지 않은 자만 접근(인증자접근 안됨)
		.antMatchers("/project/write",
					"/project/view/**").authenticated() // 인증만 되면 들어갈 수 있음
		.antMatchers("/api/**").permitAll()
		.antMatchers(
                "/v3/**",
                "/v2/**",
                "/swagger*/**").permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers("/images/**").permitAll()
        .anyRequest().authenticated() // 이외의 모든 request들은 인증이 되어야만 한다
		.and()
				.formLogin()
				.loginPage("/loginForm")
				.usernameParameter("username")/*default=username*/
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/")
		.and()
				.oauth2Login()
				.loginPage("/loginForm")
				.userInfoEndpoint()
				.userService(principalOAuth2UserService);
		
		// http.csrf().ignoringAntMatchers("/h2-console/**"); // 이전코드
		http.headers().frameOptions().disable();
		/* h2-console 은 iframe을 사용.
		* Spring security를 사용하면 http header에 X-Frame-Options가 추가되어
		* iframe으로 동작하던 기능이 정상적으로 동작하지 않아 생겼던 현상이었으며
		* 위의 코드는 X-Frame-Options header를 추가하는 부분을 비활성화한 것
		*/

	}

	/**
	 * 비밀번호 암호화시키는 객체 ioc등록
	 */
	@Bean
	static BCryptPasswordEncoder encodePwd(){
		return new BCryptPasswordEncoder();
	}

}
