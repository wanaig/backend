package com.naixue.config;

import com.naixue.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC配置类
 *
 * 配置:
 * - JWT拦截器 (认证)
 * - CORS跨域支持
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    /** JWT拦截器 */
    private final JwtInterceptor jwtInterceptor;

    /**
     * 配置JWT拦截器
     *
     * 需要认证的接口:
     * - /member/info
     * - /address/**
     * - /order/**
     * - /coupon/**
     * - /points/**
     * - /attendance/**
     * - /recharge/**
     *
     * 不需要认证的接口:
     * - /member/wxlogin (微信一键登录)
     * - /member/login (登录)
     * - /store (门店)
     * - /goods (商品)
     * - /goodsCategory (商品分类)
     * - /banner (轮播图)
     * - /static/** (静态资源)
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/member/wxlogin",
                        "/member/login",
                        "/store",
                        "/goods",
                        "/goodsCategory",
                        "/banner",
                        "/static/**"
                );
    }

    /**
     * 配置CORS跨域支持
     *
     * 允许所有来源、所有HTTP方法的跨域请求
     * 适用于前后端分离架构
     *
     * @param registry CORS注册表
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
