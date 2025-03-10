package com.bovo.Bovo.config;

import com.bovo.Bovo.modules.user.security.JwtFilter;
import com.bovo.Bovo.modules.user.security.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 기능 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .cors(cors -> cors.configurationSource(corsConfig()))
                .authorizeHttpRequests(auth -> { // 요청(URL)에 대한 접근 제어 설정
//                    auth.requestMatchers("/**", "/error", "/static/**").permitAll(); // 개발 중 임시 인증 없이 허용
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); // Preflight 요청 허용
                    auth.requestMatchers(HttpMethod.POST,"/","/login", "/my-page/logout", "/register", "/register/nickname", "/register/email", "/refresh").permitAll(); // 로그인과 회원가입 인증 없이 허용
                    auth.requestMatchers(HttpMethod.GET, "/my-page/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/**").permitAll();
                    auth.requestMatchers(HttpMethod.DELETE, "/**").authenticated();
                    auth.requestMatchers(HttpMethod.PUT, "/**").authenticated();
                    auth.requestMatchers(HttpMethod.POST, "/**").authenticated(); // 위 요청 외에는 인증 필요
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Jwt 인증 필터 추가
                .build();

    }

    @Bean
    public CorsConfigurationSource corsConfig() {
        String ngrokUrl = System.getenv("NGROK_URL");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:5173"); // 프론트 주소
        corsConfiguration.addAllowedOrigin(ngrokUrl);
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
        corsConfiguration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
        corsConfiguration.setAllowCredentials(true); // 쿠키 허용
        corsConfiguration.addExposedHeader("Set-Cookie");
        corsConfiguration.setAllowCredentials(true); // JWT 토큰을 쿠키에 넣어서 전달하므로

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }
}
