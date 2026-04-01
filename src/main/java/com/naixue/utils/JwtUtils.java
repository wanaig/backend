package com.naixue.utils;

import com.naixue.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * 提供JWT Token的生成和验证功能
 *
 * Token中包含:
 * - memberId: 会员ID
 * - openid: 微信OpenID
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    /** JWT配置 */
    private final JwtConfig jwtConfig;

    /**
     * 生成JWT Token
     *
     * @param memberId 会员ID
     * @param openid 微信OpenID
     * @return JWT Token字符串
     */
    public String generateToken(Long memberId, String openid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);
        claims.put("openid", openid);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析JWT Token
     *
     * @param token JWT Token字符串
     * @return Claims对象 (包含用户信息)
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.warn("Token 已过期");
            return null;
        } catch (JwtException e) {
            log.warn("无效 Token");
            return null;
        }
    }

    /**
     * 获取会员ID
     *
     * @param token JWT Token字符串
     * @return 会员ID
     */
    public Long getMemberId(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.get("memberId", Long.class) : null;
    }

    /**
     * 验证Token是否有效
     *
     * @param token JWT Token字符串
     * @return true-有效 false-无效
     */
    public boolean validateToken(String token) {
        return parseToken(token) != null;
    }

    /**
     * 获取签名密钥
     *
     * @return 密钥对象
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
