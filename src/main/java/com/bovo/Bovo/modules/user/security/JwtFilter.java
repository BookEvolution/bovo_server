package com.bovo.Bovo.modules.user.security;

import com.bovo.Bovo.modules.user.service.UserService;
import com.bovo.Bovo.modules.user.service.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String SecretKey;
    private final JwtProvider jwtProvider;

    public JwtFilter(@Value("${jwt.secretkey}") String secretKey, JwtProvider jwtProvider) {
        this.SecretKey = secretKey;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestURI = request.getRequestURI();

        if (requestURI.equals("/") || requestURI.equals("/refresh") || requestURI.contains("/login") || requestURI.contains("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("authorization: "+ authorization);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            logger.error("authorization이 없음");
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.split(" ")[1];
        int result = jwtProvider.ExpiredAccessToken(accessToken, SecretKey);
        if (result == 201) {
            filterChain.doFilter(request,response);
            return;
        } else if (result == 401) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = "{\"status\": \"401\", \"message\": \"엑세스 토큰 만료\"}";
            response.getWriter().write(jsonResponse);

            return; // 엑세스 토큰 만료 -> 리프레쉬 토큰
        } else if (result == 403) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonResponse = "{\"status\": \"403\", \"message\": \"재로그인 권장\"}";
            response.getWriter().write(jsonResponse);
            return; // 로그아웃 후 재로그인 권장
        }

    }
}
