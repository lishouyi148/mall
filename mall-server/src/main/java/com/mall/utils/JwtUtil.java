package com.mall.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String secret;
    private final long expiration;
    private final String tokenName;

    // 使用@Value注解注入配置值
    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.token-name}") String tokenName
    ) {
        this.secret = secret;
        this.expiration = expiration;
        this.tokenName = tokenName;
    }

    // JwtUtil.java
    public String generateToken(Integer userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        // 添加ROLE_前缀
        claims.put("role", "ROLE_" + role);
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // 解析JWT
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // 验证Token是否有效
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 添加一个方法获取tokenName
    public String getTokenName() {
        return tokenName;
    }
}