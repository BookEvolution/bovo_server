package com.bovo.Bovo.modules.user.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

    // 엑세스 토큰 발급
    public String createAccessToken(Long userid, String SecretKey, long expireTimeAccess) {
        return Jwts.builder()
                .claim("userid", userid)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeAccess))
                .signWith(SignatureAlgorithm.HS256, SecretKey)
                .compact();
    }

    // 리프레쉬 토큰 발급
    public String createRefreshToken(Long userid, String SecretKey, long expireTimeRefresh) {
        return Jwts.builder()
                .claim("userid", userid)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeRefresh))
                .signWith(SignatureAlgorithm.HS256, SecretKey)
                .compact();
    }

    // 엑세스 토큰 검증 로직
    public ResponseEntity<Map<String, Object>> ExpiredAccessToken(String accessToken, String SecretKey) {
        try {
            Jwts.parser()
                    .setSigningKey(SecretKey)
                    .build()
                    .parseClaimsJws(accessToken);
            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("message", "엑세스 토큰 검증 성공");
            return ResponseEntity.ok(response); // 서명과 유효기간 모두 통과
            // 엑세스 토큰 서명 검증 실패 시, JwtException
            // 엑세스 토큰 유효 기간 만료 시, ExpiredJwtException
        } catch (ExpiredJwtException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 401);
            response.put("message", "만료된 엑세스 토큰입니다.");
            return ResponseEntity.ok(response);
        } catch (JwtException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 403);
            response.put("message", "유효하지 않은 엑세스 토큰"); // 클라이언트는 로그아웃 처리하고 로그인 페이지 이동 권장
            return ResponseEntity.ok(response);
        }
    }

    // 리프레쉬 토큰 검증 로직 -> 이것까지 만료되면 로그아웃 후 로그인 페이지로 이동 권장
    public ResponseEntity<Map<String, Object>> ExpiredRefreshToken(String refreshToken, String SecretKey) {
        try {
            Jwts.parser()
                    .setSigningKey(SecretKey)
                    .build()
                    .parseClaimsJws(refreshToken);
            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("message", "리프레쉬 토큰 검증 성공");
            return ResponseEntity.ok(response); // 서명과 유효기간 모두 통과
            // 엑세스 토큰 서명 검증 실패 시, JwtException
            // 엑세스 토큰 유효 기간 만료 시, ExpiredJwtException
        } catch (ExpiredJwtException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 401);
            response.put("message", "만료된 리프레쉬 토큰입니다.");
            return ResponseEntity.ok(response);
        } catch (JwtException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 403);
            response.put("message", "유효하지 않은 리프레쉬 토큰"); // 클라이언트는 로그아웃 처리하고 로그인 페이지 이동 권장
            return ResponseEntity.ok(response);
        }
    }

}
