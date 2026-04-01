package com.naixue.dto;

import lombok.Data;

/**
 * 微信一键登录请求DTO
 *
 * 用于接收小程序端的微信一键登录请求参数
 *
 * 一键登录流程:
 * 1. 小程序调用 wx.login() 获取 code
 * 2. 将 code 发送到后端 /member/wxlogin
 * 3. 后端用 code 换取 openid
 * 4. 根据 openid 查询或创建会员
 * 5. 返回 JWT Token
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class LoginDTO {

    /**
     * 微信授权code
     * 通过 wx.login() 获取
     * 用于调用微信接口验证用户身份
     * 每次调用 wx.login() 会生成新的 code
     * code 有效期为5分钟
     */
    private String code;
}
