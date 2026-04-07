package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.dto.LoginDTO;
import com.naixue.dto.UpdateMemberDTO;
import com.naixue.service.MemberService;
import com.naixue.vo.LoginVO;
import com.naixue.vo.MemberInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 会员控制器
 *
 * 提供会员相关的API接口
 *
 * 接口列表:
 * - POST /member/wxlogin - 微信一键登录
 * - GET /member/info - 获取会员信息
 * - PUT /member/info - 更新会员信息
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    /** 会员服务 */
    private final MemberService memberService;

    /**
     * 微信一键登录注册
     *
     * POST /member/wxlogin
     *
     * 一键登录流程:
     * 1. 小程序调用 wx.login() 获取 code
     * 2. POST /member/wxlogin { code: "xxx" }
     * 3. 后端自动用 code 换 openid → 查询/创建会员 → 返回 token
     * 4. 小程序保存 token 到 Vuex
     *
     * @param dto 登录参数 { code: "微信code" }
     * @return 登录结果 { token, customerId, nickname, avatar, isNewMember }
     */
    @PostMapping("/wxlogin")
    public Result<LoginVO> wxLogin(@RequestBody LoginDTO dto) {
        LoginVO vo = memberService.login(dto);
        Result<LoginVO> success = Result.success(vo);
        System.out.println( success);
        return success;
    }

    /**
     * 抖音一键登录注册
     *
     * POST /member/douyinlogin
     *
     * 一键登录流程:
     * 1. 小程序调用 tt.login() 获取 code
     * 2. POST /member/douyinlogin { code: "xxx" }
     * 3. 后端自动用 code 换 openid → 查询/创建会员 → 返回 token
     * 4. 小程序保存 token
     *
     * @param dto 登录参数 { code: "抖音code" }
     * @return 登录结果 { token, customerId, nickname, avatar, isNewMember }
     */
    @PostMapping("/douyinlogin")
    public Result<LoginVO> douyinLogin(@RequestBody LoginDTO dto) {
        LoginVO vo = memberService.douyinLogin(dto);
        return Result.success(vo);
    }

    /**
     * 手机号登录/注册
     *
     * POST /member/phonelogin
     *
     * 登录流程:
     * 1. 接收手机号参数
     * 2. 查询数据库该手机号是否已注册
     * 3. 已注册: 返回登录态(token + 用户信息)
     * 4. 未注册: 自动创建新用户,返回登录态(token + 用户信息)
     *
     * @param dto 登录参数 { phone: "13812345678" }
     * @return 登录结果 { token, customerId, nickname, avatar, isNewMember }
     */
    @PostMapping("/phonelogin")
    public Result<LoginVO> phoneLogin(@RequestBody LoginDTO dto) {
        LoginVO vo = memberService.phoneLogin(dto);
        return Result.success(vo);
    }

    /**
     * 获取会员信息
     *
     * GET /member/info
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @return 会员信息
     */
    @GetMapping("/info")
    public Result<MemberInfoVO> getMemberInfo(@RequestAttribute Long memberId) {
        MemberInfoVO vo = memberService.getMemberInfo(memberId);
        return Result.success(vo);
    }

    /**
     * 更新会员信息
     *
     * PUT /member/info
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param nickname 昵称 (可选)
     * @param avatar 头像 (可选)
     * @param gender 性别 (可选)
     * @param birthday 生日 (可选)
     * @return 成功标识
     */
    @PutMapping("/info")
    public Result<Void> updateMemberInfo(
            @RequestAttribute Long memberId,
            @RequestBody UpdateMemberDTO dto) {
        memberService.updateMemberInfo(memberId, dto);
        return Result.success();
    }
}
