package com.example.myproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/*/**").permitAll()
		.antMatchers("/",
				"/api/**").permitAll()
		.antMatchers(
                "/v3/**",
                "/v2/**",
                "/swagger*/**").permitAll()
		.antMatchers("/h2-console/**").permitAll()
        .anyRequest().authenticated()
		.and()
		.csrf().disable();
		
		//http.csrf().ignoringAntMatchers("/h2-console/**");
		
		http.headers().frameOptions().disable();//h2-console 은 iframe을 사용.
		/*Spring security를 사용하면 http header에 X-Frame-Options가 추가되어
		iframe으로 동작하던 기능이 정상적으로 동작하지 않아 생겼던 현상이었으며 
		위의 코드는 X-Frame-Options header를 추가하는 부분을 비활성화한 것 */
	}

}
