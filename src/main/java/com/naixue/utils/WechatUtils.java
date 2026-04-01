package com.naixue.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.naixue.config.WechatConfig;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.util.Base64;

/**
 * 微信工具类
 *
 * 提供微信小程序相关的API调用功能
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WechatUtils {

    /** 微信配置 */
    private final WechatConfig wechatConfig;

    /**
     * 微信code2session接口URL
     */
    private static final String CODE2SESSION_URL =
        "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 调用微信接口将code换取session
     *
     * 通过wx.login()获取的code,换取用户的openid和session_key
     *
     * @param code 微信登录code
     * @return 包含openid、session_key、unionid的结果
     */
    public WechatSessionResult code2Session(String code) {
        String url = CODE2SESSION_URL + "?appid=" + wechatConfig.getAppid()
                   + "&secret=" + wechatConfig.getSecret()
                   + "&js_code=" + code
                   + "&grant_type=authorization_code";

        try {
            // 调用微信接口
            String response = HttpUtil.get(url);
            JSONObject json = JSON.parseObject(response);

            // 检查错误码
            Integer errcode = json.getInteger("errcode");
            if (errcode != null && errcode != 0) {
                throw new RuntimeException(json.getString("errmsg"));
            }

            // 返回结果
            return new WechatSessionResult(
                json.getString("openid"),
                json.getString("session_key"),
                json.getString("unionid")
            );
        } catch (Exception e) {
            log.error("微信 code2Session 失败", e);
            throw new RuntimeException("微信登录失败");
        }
    }

    /**
     * 微信小程序手机号解密
     *
     * 通过button组件获取的加密数据,使用session_key进行解密
     *
     * @param sessionKey 会话密钥
     * @param encryptedData 加密的用户手机号数据
     * @param iv 加密算法的初始向量
     * @return 解密后的手机号
     */
    public String decryptPhoneNumber(String sessionKey, String encryptedData, String iv) {
        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(sessionKey.getBytes(StandardCharsets.UTF_8), "AES");
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, params);

            // 解密
            byte[] decrypted = cipher.doFinal(
                Base64.getDecoder().decode(encryptedData)
            );

            // 转换为字符串并解析JSON
            String jsonStr = new String(decrypted, StandardCharsets.UTF_8);
            JSONObject json = JSON.parseObject(jsonStr);
            return json.getString("phoneNumber");

        } catch (Exception e) {
            log.error("微信手机号解密失败", e);
            return null;
        }
    }

    /**
     * 微信Session结果类
     *
     * @author naixue-backend
     */
    @Data
    @lombok.AllArgsConstructor
    public static class WechatSessionResult {
        /** 用户唯一标识 */
        private String openid;

        /** 会话密钥 */
        private String sessionKey;

        /** 用户在开放平台的唯一标识符 */
        private String unionid;
    }
}
