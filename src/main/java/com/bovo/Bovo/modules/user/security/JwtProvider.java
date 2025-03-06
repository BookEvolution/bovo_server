package com.bovo.Bovo.modules.user.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtProvider {

    // 엑세스 토큰 발급
    public String createAccessToken(Integer userid, String SecretKey, long expireTimeAccess) {
        return Jwts.builder()
                .claim("userid", userid)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expireTimeAccess)))
                .signWith(Keys.hmacShaKeyFor(SecretKey.getBytes()))
                .compact();
    }

    // 리프레쉬 토큰 발급
    public String createRefreshToken(Integer userid, String SecretKey, long expireTimeRefresh) {
        return Jwts.builder()
                .claim("userid", userid)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expireTimeRefresh)))
                .signWith(Keys.hmacShaKeyFor(SecretKey.getBytes()))
                .compact();
    }

    // 엑세스 토큰 검증 로직
    public int ExpiredAccessToken(String accessToken, String SecretKey){
        try {
            JwtDecoder jwtDecoder = createJwtDecoder(SecretKey);
            Jwt jwt = jwtDecoder.decode(accessToken);
            return 200;
        } catch (ExpiredJwtException e) {
            return 401;
        } catch (JwtException e) {
            return 403;
        }
    }

    // 리프레쉬 토큰 검증 로직 -> 이것까지 만료되면 로그아웃 후 로그인 페이지로 이동 권장
    public int ExpiredRefreshToken(String refreshToken, String SecretKey){
        try {
            JwtDecoder jwtDecoder = createJwtDecoder(SecretKey);
            Jwt jwt = jwtDecoder.decode(refreshToken);
            return 200;
        } catch (JwtException e) {
            return 403;
        }
    }

    // 엑세스 토큰에서 사용자 정보(UserId) 추출
    public Integer ExtractUserIdFromAccessToken(String accessToken, String SecretKey) {
        JwtDecoder jwtDecoder = createJwtDecoder(SecretKey);
        Jwt jwt = jwtDecoder.decode(accessToken);
        return jwt.getClaim("userid"); // "userId" 클레임에서 값 추출
    }

    // 리프레쉬 토큰에서 사용자 정보(UserId) 추출
    public Integer ExtractUserIdFromRefreshToken(String refreshToken, String SecretKey) {
        JwtDecoder jwtDecoder = createJwtDecoder(SecretKey);
        Jwt jwt = jwtDecoder.decode(refreshToken);
        return jwt.getClaim("userid"); // "userId" 클레임에서 값 추출
    }

    private JwtDecoder createJwtDecoder(String SecretKey) {
        byte[] keyBytes = SecretKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }

}
