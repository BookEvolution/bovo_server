package com.bovo.Bovo.modules.user.security;

import com.bovo.Bovo.modules.user.service.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserServiceImpl userService;
    @Value("${jwt.secretkey}")
    private final String SecretKey;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "엑세스 토큰 만료");
            return; // 엑세스 토큰 만료 -> 리프레쉬 토큰
        } else if (result == 403) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "재로그인 권장");
            return; // 로그아웃 후 재로그인 권장
        }

    }
}
