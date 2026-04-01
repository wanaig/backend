package com.naixue.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信小程序配置类
 *
 * 从application.yml读取微信相关配置
 *
 * 配置项:
 * - wechat.appid: 微信小程序AppID
 * - wechat.secret: 微信小程序AppSecret
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {

    /**
     * 微信小程序AppID
     * 用于调用微信API
     */
    private String appid;

    /**
     * 微信小程序AppSecret
     * 用于调用微信API (注意保密,不要泄露)
     */
    private String secret;
}
