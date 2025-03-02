package com.bovo.Bovo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security의 보안 설정을 활성화하는 역할
public class securityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 기능 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .authorizeHttpRequests(auth -> auth // 요청(URL)에 대한 접근 제어 설정
                        .requestMatchers("/", "/error", "/static/**").permitAll() // 개발 중 임시 인증 없이 허용
                        .requestMatchers("/login", "/register").permitAll() // 로그인과 회원가입 인증 없이 허용
                        .anyRequest().authenticated() // 위 요청 외에는 인증 필요
                )
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 개발 중 임시, 모든 요청 인증 없이 허용
                .build();

    }
}
