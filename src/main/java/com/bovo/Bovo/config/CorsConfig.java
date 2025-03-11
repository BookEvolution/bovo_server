package com.bovo.Bovo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173", "https://4e02-165-246-206-167.ngrok-free.app")); // ngrok 사용하실 때 사용하시는 ngrok 주소로 변경해주세요.
        System.out.println("CORS Allowed Origins: " + corsConfiguration.getAllowedOrigins());
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD")); // 허용할 HTTP 메서드
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.addExposedHeader("Set-Cookie");
        corsConfiguration.setAllowCredentials(true); // JWT 토큰을 쿠키에 넣어서 전달하므로

        System.out.println("Allowed Origins: " + corsConfiguration.getAllowedOrigins());
        System.out.println("Allowed Methods: " + corsConfiguration.getAllowedMethods());
        System.out.println("Allowed Headers: " + corsConfiguration.getAllowedHeaders());
        System.out.println("Allow Credentials: " + corsConfiguration.getAllowCredentials());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
