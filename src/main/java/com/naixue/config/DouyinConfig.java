package com.naixue.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 抖音小程序配置类
 *
 * 从application.yml读取抖音相关配置
 *
 * 配置项:
 * - douyin.clientKey: 抖音小程序ClientKey
 * - douyin.clientSecret: 抖音小程序ClientSecret
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "douyin")
public class DouyinConfig {

    /**
     * 抖音小程序ClientKey
     * 用于调用抖音OAuth2.0接口
     */
    private String clientKey;

    /**
     * 抖音小程序ClientSecret
     * 用于调用抖音OAuth2.0接口 (注意保密,不要泄露)
     */
    private String clientSecret;
}
