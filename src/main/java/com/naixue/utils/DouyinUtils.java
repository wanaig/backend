package com.naixue.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.naixue.config.DouyinConfig;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 抖音工具类
 *
 * 提供抖音小程序相关的API调用功能
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DouyinUtils {

    /** 抖音配置 */
    private final DouyinConfig douyinConfig;

    /**
     * 抖音access_token接口URL (新)
     */
    private static final String ACCESS_TOKEN_URL =
        "https://open.douyin.com/passport/open/access_token";

    /**
     * 调用抖音接口将code换取openid
     *
     * 抖音OAuth2.0流程:
     * 1. 前端调用 tt.login() 获取 code
     * 2. 后端用 code + client_key + client_secret 换取 openid
     *
     * @param code 抖音登录code，通过 tt.login() 获取
     * @return 包含openid的结果
     */
    public DouyinSessionResult code2Session(String code) {
        try {
            // 调用抖音接口 - 使用POST方式，带上URL参数
            cn.hutool.http.HttpRequest request = cn.hutool.http.HttpUtil.createPost(ACCESS_TOKEN_URL)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .form("client_key", douyinConfig.getClientKey())
                    .form("client_secret", douyinConfig.getClientSecret())
                    .form("code", code)
                    .form("grant_type", "authorization_code");

            cn.hutool.http.HttpResponse response = request.setFollowRedirects(true).execute();
            String body = response.body();

            log.info("抖音API响应状态: {}, body: {}", response.getStatus(), body);

            if (body == null || body.isEmpty()) {
                log.error("抖音API返回空响应");
                throw new RuntimeException("抖音登录失败: 空响应");
            }

            // 响应可能是HTML重定向页面，不是JSON
            if (body.trim().startsWith("<")) {
                log.error("抖音API返回非JSON响应: {}", body);
                throw new RuntimeException("抖音登录失败: 非JSON响应");
            }

            JSONObject json = JSON.parseObject(body);

            // 检查抖音的错误码 - 抖音使用 error_code 字段
            JSONObject data = json.getJSONObject("data");
            if (data != null) {
                Integer errorCode = data.getInteger("error_code");
                if (errorCode != null && errorCode != 0) {
                    String description = data.getString("description");
                    log.error("抖音API错误: error_code={}, description={}", errorCode, description);
                    throw new RuntimeException(description != null ? description : "抖音登录失败");
                }

                // 解析返回的openid
                String openid = data.getString("openid");
                if (openid != null && !openid.isEmpty()) {
                    return new DouyinSessionResult(openid);
                }
            }

            // 检查message字段
            String message = json.getString("message");
            if ("error".equals(message)) {
                String description = data != null ? data.getString("description") : "未知错误";
                throw new RuntimeException(description != null ? description : "抖音登录失败");
            }

            log.error("抖音API响应无openid: {}", json.toJSONString());
            throw new RuntimeException("抖音登录失败: 无openid");

            // 返回结果
//            return new DouyinSessionResult(openid);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("抖音 code2Session 失败", e);
            throw new RuntimeException("抖音登录失败");
        }
    }

    /**
     * 抖音Session结果类
     *
     * @author naixue-backend
     */
    @Data
    @lombok.AllArgsConstructor
    public static class DouyinSessionResult {
        /** 用户唯一标识 */
        private String openid;
    }
}
