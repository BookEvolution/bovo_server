package com.bovo.Bovo.config;

import com.bovo.Bovo.modules.user.security.JwtFilter;
import com.bovo.Bovo.modules.user.security.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // Spring Security의 보안 설정을 활성화하는 역할
public class securityConfig {
    private JwtFilter jwtFilter;

    public securityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 기능 비활성화
                .formLogin(form -> form.disable()) // 기본 로그인 페이지 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .authorizeHttpRequests(auth -> { // 요청(URL)에 대한 접근 제어 설정
                    auth.requestMatchers("/**", "/error", "/static/**").permitAll(); // 개발 중 임시 인증 없이 허용
//                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); // Preflight 요청 허용
//                    auth.requestMatchers("/","/login", "/register").permitAll(); // 로그인과 회원가입 인증 없이 허용
//                    auth.requestMatchers(HttpMethod.POST, "/**").authenticated(); // 위 요청 외에는 인증 필요
                })
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                // Jwt 인증 필터 추가
                .cors(cors -> cors.configurationSource(corsConfig()))
                .build();

    }

    @Bean
    public CorsConfigurationSource corsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:5173"); // 프론트 주소
        corsConfiguration.addAllowedMethod("*"); //모든 HTTP 메서드 허용
        corsConfiguration.addAllowedHeader("*"); // 모든 헤더 허용
        corsConfiguration.setAllowCredentials(true); // JWT 토큰을 쿠키에 넣어서 전달하므로

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }
}
