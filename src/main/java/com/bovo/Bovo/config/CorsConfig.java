package com.bovo.Bovo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 경로 CORS 허용
                        .allowedOrigins("http://localhost:5173") // 프론트 주소
                        .allowedMethods("*") //모든 HTTP 메서드 허용
                        .allowedHeaders("*") // 모든 헤더 허용
                        .allowCredentials(true); // JWT 토큰을 쿠키에 넣어서 전달하므로
            }
        };
    }
}
