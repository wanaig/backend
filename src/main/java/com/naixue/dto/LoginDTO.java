package com.naixue.dto;

import lombok.Data;

/**
 * 一键登录请求DTO
 *
 * 用于接收小程序端的一键登录请求参数
 *
 * 一键登录流程:
 * 1. 小程序调用 wx.login() 或 tt.login() 获取 code
 * 2. 将 code 发送到后端 /member/wxlogin 或 /member/douyinlogin
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
     * 授权code
     * 微信: 通过 wx.login() 获取
     * 抖音: 通过 tt.login() 获取
     * 用于调用对应平台接口验证用户身份
     * 每次调用 login() 会生成新的 code
     * code 有效期为5分钟
     */
    private String code;

    /**
     * 登录类型
     * 不传或传 "wechat": 微信登录
     * 传 "douyin": 抖音登录
     */
    private String loginType;

    /**
     * 手机号
     * 用于手机号登录/注册
     */
    private String phone;
}
