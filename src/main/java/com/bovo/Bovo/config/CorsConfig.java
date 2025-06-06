package com.bovo.Bovo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        CORS설정
//        로컬: localhost:5173
//        Netlify: https://bovo.netlify.app/
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173", "https://bovo.netlify.app"));
        log.info("CORS Allowed Origins: {}", corsConfiguration.getAllowedOrigins());
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD")); // 허용할 HTTP 메서드
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.addExposedHeader("Set-Cookie");
        corsConfiguration.setAllowCredentials(true); // JWT 토큰을 쿠키에 넣어서 전달하므로

        log.info("Allowed Origins: {}", corsConfiguration.getAllowedOrigins());
        log.info("Allowed Methods: {}", corsConfiguration.getAllowedMethods());
        log.info("Allowed Headers: {}", corsConfiguration.getAllowedHeaders());
        log.info("Allow Credentials: {}", corsConfiguration.getAllowCredentials());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
