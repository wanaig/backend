package com.naixue.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 *
 * 从application.yml读取JWT相关配置
 *
 * 配置项:
 * - jwt.secret: JWT签名密钥
 * - jwt.expiration: Token过期时间(秒)
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * JWT签名密钥
     * 用于生成和验证Token
     */
    private String secret;

    /**
     * Token过期时间
     * 单位: 秒
     * 默认: 604800 (7天)
     */
    private Long expiration;
}
