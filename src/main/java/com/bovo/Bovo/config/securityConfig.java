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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity // Spring Security의 보안 설정을 활성화하는 역할
public class securityConfig {
    private final JwtFilter jwtFilter;

    public securityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig 초기화 시작!");
        return http
                .cors(cors -> {
                    cors.configurationSource(corsConfig());
                    System.out.println("CORS 설정 적용 완료");
                })
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 기능 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .authorizeHttpRequests(auth -> { // 요청(URL)에 대한 접근 제어 설정
//                    auth.requestMatchers("/**", "/error", "/static/**").permitAll(); // 개발 중 임시 인증 없이 허용
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); // Preflight 요청 허용
                    System.out.println("OPTIONS 요청 허용됨");
                    auth.requestMatchers(HttpMethod.POST,"/","/login", "/my-page/logout", "/register", "/register/nickname", "/register/email", "/refresh").permitAll(); // 로그인과 회원가입 인증 없이 허용
                    auth.requestMatchers(HttpMethod.GET, "/my-page/**").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/**").permitAll();
                    auth.requestMatchers(HttpMethod.DELETE, "/**").authenticated();
                    auth.requestMatchers(HttpMethod.PUT, "/**").authenticated();
                    auth.requestMatchers(HttpMethod.POST, "/**").authenticated(); // 위 요청 외에는 인증 필요
                    auth.anyRequest().permitAll(); // 모든 요청 기본 허용 (테스트 후 조정)
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Jwt 인증 필터 추가
                .build();

    }

    @Bean
    public CorsConfigurationSource corsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173", "https://6f28-112-158-33-80.ngrok-free.app"));
        System.out.println("CORS Allowed Origins: " + corsConfiguration.getAllowedOrigins());
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD")); // 허용할 HTTP 메서드
//        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.addExposedHeader("Set-Cookie");
        corsConfiguration.setAllowCredentials(true); // JWT 토큰을 쿠키에 넣어서 전달하므로

        System.out.println("🔹 Allowed Origins: " + corsConfiguration.getAllowedOrigins());
        System.out.println("🔹 Allowed Methods: " + corsConfiguration.getAllowedMethods());
        System.out.println("🔹 Allowed Headers: " + corsConfiguration.getAllowedHeaders());
        System.out.println("🔹 Allow Credentials: " + corsConfiguration.getAllowCredentials());

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173", "https://6f28-112-158-33-80.ngrok-free.app")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEADs")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
