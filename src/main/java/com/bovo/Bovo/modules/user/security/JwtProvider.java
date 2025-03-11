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
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    // 엑세스 토큰 발급
    public String createAccessToken(Integer userid, String SecretKey, long expireTimeAccess) {
        System.out.println("현재 사용 중인 SecretKey: " + SecretKey);

        String accessToken = Jwts.builder()
                .claim("userid", userid)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expireTimeAccess)))
                .signWith(Keys.hmacShaKeyFor(SecretKey.getBytes()))
                .compact();

        System.out.println("🔍 [DEBUG] 발급된 JWT: " + accessToken);
        return accessToken;
//        return Base64.getUrlEncoder().withoutPadding().encodeToString(accessToken.getBytes());
    }

    // 리프레쉬 토큰 발급
    public String createRefreshToken(Integer userid, String SecretKey, long expireTimeRefresh) {
        System.out.println("현재 사용 중인 SecretKey: " + SecretKey);

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
            System.out.println("현재 사용 중인 SecretKey: " + SecretKey);

            JwtDecoder jwtDecoder = createJwtDecoder(SecretKey);
            Jwt jwt = jwtDecoder.decode(accessToken);
            return 200;
        } catch (ExpiredJwtException e) {
            System.out.println("엑세스 토큰 만료: " + e.getMessage());
            return 401;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            System.out.println("서명 불일치: " + e.getMessage());
            return 403;
        } catch (JwtException e) {
            System.out.println("JWT 검증 실패: " + e.getMessage());
            return 403;
        }
    }

    // 리프레쉬 토큰 검증 로직 -> 이것까지 만료되면 로그아웃 후 로그인 페이지로 이동 권장
    public boolean ExpiredRefreshToken(String refreshToken, String SecretKey){
        try {
            System.out.println("현재 사용 중인 SecretKey: " + SecretKey);

            JwtDecoder jwtDecoder = createJwtDecoder(SecretKey);
            Jwt jwt = jwtDecoder.decode(refreshToken);
            return false;
        } catch (JwtException e) {
            return true;
        }
    }

    // 엑세스 토큰에서 사용자 정보(UserId) 추출
    public Integer ExtractUserIdFromAccessToken(String accessToken, String SecretKey) {
        try {
            JwtDecoder jwtDecoder = createJwtDecoder(SecretKey);
            Jwt jwt = jwtDecoder.decode(accessToken);

            Object userIdObj = jwt.getClaim("userid"); // "userId" 클레임에서 값 추출 -> Long/ Integer 구분 불가
            if (userIdObj == null) {
                throw new JwtException("JWT에서 userid 클레임을 찾을 수 없음");
            }
            System.out.println("Extracted userIdObj: " + userIdObj + " (Type: " + userIdObj.getClass().getSimpleName() + ")");

            if (userIdObj instanceof Number) {
                return ((Number) userIdObj).intValue(); // Integer 타입으로 리턴
            }
            return null;
        }catch (JwtException e){
            System.out.println("JWT 검증 실패: " + e.getMessage());
            return null;
        }
    }

    // 리프레쉬 토큰에서 사용자 정보(UserId) 추출
    public Integer ExtractUserIdFromRefreshToken(String refreshToken, String SecretKey) {
        try {

            JwtDecoder jwtDecoder = createJwtDecoder(SecretKey);
            Jwt jwt = jwtDecoder.decode(refreshToken);

            Object userIdObj = jwt.getClaim("userid"); // "userId" 클레임에서 값 추출 -> Long/ Integer 구분 불가

            if (userIdObj == null) {
                throw new JwtException("JWT에서 userid 클레임을 찾을 수 없음");
            }

            System.out.println("Extracted userIdObj: " + userIdObj + " (Type: " + userIdObj.getClass().getSimpleName() + ")");

            if (userIdObj instanceof Number) {
                return ((Number) userIdObj).intValue(); // Integer 타입으로 리턴
            }
            return null;
        } catch (JwtException e) {
            System.out.println("JWT 검증 실패: " + e.getMessage());
            return null;
        }
    }

    private JwtDecoder createJwtDecoder(String SecretKey) {
        byte[] keyBytes = SecretKey.getBytes();

        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("SecretKey 길이가 32바이트 이상이어야 합니다.");
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }

}