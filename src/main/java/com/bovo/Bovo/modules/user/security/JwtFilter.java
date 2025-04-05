package com.bovo.Bovo.modules.user.security;

import com.bovo.Bovo.modules.kakaoLogin.service.KakaoService;
import com.bovo.Bovo.modules.kakaoLogin.service.SocialUserDetailsService;
import com.bovo.Bovo.modules.user.dto.security.AuthenticatedUserId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String SecretKey;
    private final JwtProvider jwtProvider;
    private final SocialUserDetailsService socialUserDetailsService;
    private final KakaoService kakaoService;

    public JwtFilter(@Value("${jwt.secretkey}") String secretKey, JwtProvider jwtProvider, SocialUserDetailsService socialUserDetailsService, KakaoService kakaoService) {
        this.SecretKey = secretKey;
        this.jwtProvider = jwtProvider;
        this.socialUserDetailsService = socialUserDetailsService;
        this.kakaoService = kakaoService;
        log.info("JwtFilter 생성");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestURI = request.getRequestURI();
        log.info("현재 요청: {}", requestURI);

        if (request.getMethod().equals("OPTIONS")) {
            log.info("OPTIONS 요청 - CORS 프리플라이트 통과");
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getMethod().equals("GET")) {
            log.info("GET 요청 도착: {}", requestURI);
        }

        if (request.getMethod().equals("GET") && !requestURI.contains("/my-page")
                && !requestURI.contains("/main") && !requestURI.contains("/search")
                && !requestURI.contains("/book-info") && !requestURI.contains("/archive")
                && !requestURI.contains("/chatroom") && !requestURI.contains("/rewards")) {
            log.info("GET 요청 - JwtFilter 적용 안함: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }


        if (requestURI.equals("/") || requestURI.equals("/refresh") || requestURI.contains("/login") || requestURI.equals("/register") || requestURI.contains("/logout")) {
            log.info("JwtFilter 적용 안함: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("JwtFilter 적용: {}", requestURI);

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.debug("서버가 받은 Authorization 헤더: {}", authorization);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("authorization이 없음");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("{} : 엑세스 토큰 존재", requestURI);

        String accessToken = authorization.split(" ")[1];
        log.debug("서버가 받은 JWT: {}", accessToken);
        int result = jwtProvider.ExpiredAccessToken(accessToken, SecretKey);
        if (result == 200) {
            log.info("{} : SecurityContextHolder 저장 시작", requestURI);

            Integer userId = jwtProvider.ExtractUserIdFromAccessToken(accessToken, SecretKey); // 토큰에서 userId 추출
            System.out.println("JWT에서 추출된 userId: " + userId);


            String provider = socialUserDetailsService.getProviderByUserId(userId);

            if ("KAKAO".equals(provider)) {
                String kakaoAccessToken = kakaoService.getKakaoAccessToken(userId);
                try {
                    Long kakaoId = kakaoService.getKakaoIdFromKakao(kakaoAccessToken); // 카카오 엑세스 토큰 검증 및 ID 추출
                    System.out.println("유효한 카카오 토큰 - 사용자 ID: " + kakaoId);
                } catch (Exception e) {
                    System.out.println("유효하지 않은 카카오 액세스 토큰");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"status\": \"403\", \"message\": \"유효하지 않은 카카오 액세스 토큰\"}");
                    return;
                    }
//                Long kakaoId = kakaoService.getKakaoIdFromKakao(kakaoAccessToken); // 카카오 엑세스 토큰 검증 및 ID 추출
//                System.out.println("유효한 카카오 토큰 - 사용자 ID: " + kakaoId);
            }

                UserDetails userDetails = socialUserDetailsService.loadUserByUsername(String.valueOf(userId));

                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // SecurityContextHolder에 사용자 정보 저장

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("SecurityContext에 저장된 인증 객체: " + SecurityContextHolder.getContext().getAuthentication());

                System.out.println(requestURI + ": 검증 완료");

                filterChain.doFilter(request, response);
                return;
            } else if (result == 401) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String jsonResponse = "{\"status\": \"403\", \"message\": \"엑세스 토큰 만료\"}";
                response.getWriter().write(jsonResponse);

                return; // 엑세스 토큰 만료 -> 리프레쉬 토큰
            } else if (result == 403) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String errorMessage = "재로그인 권장 (JWT 검증 실패)";
                System.out.println("403 오류: " + errorMessage);

                String jsonResponse = "{\"status\": \"403\", \"message\": \"재로그인 권장\"}";
                response.getWriter().write(jsonResponse);
                return; // 로그아웃 후 재로그인 권장
            }

        }
}
