package com.bovo.Bovo.modules.user.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    // 토큰 발급
    public String createJwtToken(Long userid, String SecretKey, long expireTime) {
        return Jwts.builder()
                .claim("userid", userid)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, SecretKey)
                .compact();
    }

    // 토큰 검증 로직

}
