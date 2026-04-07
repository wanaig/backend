package com.naixue.service;

import com.naixue.dto.LoginDTO;
import com.naixue.dto.UpdateMemberDTO;
import com.naixue.vo.LoginVO;
import com.naixue.vo.MemberInfoVO;

/**
 * 会员服务接口
 *
 * 定义会员相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public interface MemberService {

    /**
     * 微信授权登录
     *
     * 流程:
     * 1. 调用微信接口通过code获取openid
     * 2. 根据openid查询会员,不存在则创建
     * 3. 更新最后登录时间
     * 4. 生成JWT Token返回
     *
     * @param dto 登录请求参数 (包含code和loginType)
     * @return 登录结果 (Token、会员信息)
     */
    LoginVO login(LoginDTO dto);

    /**
     * 抖音授权登录
     *
     * 流程:
     * 1. 调用抖音接口通过code获取openid
     * 2. 根据openid查询会员,不存在则创建
     * 3. 更新最后登录时间
     * 4. 生成JWT Token返回
     *
     * @param dto 登录请求参数 (包含抖音code)
     * @return 登录结果 (Token、会员信息)
     */
    LoginVO douyinLogin(LoginDTO dto);

    /**
     * 手机号登录/注册
     *
     * 流程:
     * 1. 接收手机号参数
     * 2. 查询数据库该手机号是否已注册
     * 3. 已注册: 返回登录态(token + 用户信息)
     * 4. 未注册: 自动创建新用户,返回登录态(token + 用户信息)
     *
     * @param dto 登录请求参数 (包含手机号)
     * @return 登录结果 (Token、会员信息)
     */
    LoginVO phoneLogin(LoginDTO dto);

    /**
     * 获取会员信息
     *
     * @param memberId 会员ID
     * @return 会员信息
     */
    MemberInfoVO getMemberInfo(Long memberId);

    /**
     * 更新会员信息
     *
     * @param memberId 会员ID
     * @param nickname 昵称
     * @param avatar 头像
     * @param gender 性别
     * @param birthday 生日
     */
    void updateMemberInfo(Long memberId, UpdateMemberDTO dto);
}
