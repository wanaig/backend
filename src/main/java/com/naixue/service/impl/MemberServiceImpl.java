package com.naixue.service.impl;

import com.naixue.common.ResultCode;
import com.naixue.dto.LoginDTO;
import com.naixue.dto.UpdateMemberDTO;
import com.naixue.entity.Member;
import com.naixue.exception.BusinessException;
import com.naixue.mapper.MemberMapper;
import com.naixue.service.MemberService;
import com.naixue.utils.JwtUtils;
import com.naixue.utils.WechatUtils;
import com.naixue.vo.LoginVO;
import com.naixue.vo.MemberInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 会员服务实现类
 *
 * 实现MemberService接口,提供会员相关的业务逻辑
 *
 * 等级成长值配置:
 * - V1: 0, V2: 500, V3: 2000, V4: 5000, V5: 15000, V6: 50000
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    /** 会员Mapper */
    private final MemberMapper memberMapper;

    /** JWT工具类 */
    private final JwtUtils jwtUtils;

    /** 微信工具类 */
    private final WechatUtils wechatUtils;

    /**
     * 等级名称数组
     * LEVEL_NAMES[1] = "V1", LEVEL_NAMES[2] = "V2", 以此类推
     */
    private static final String[] LEVEL_NAMES = {"", "V1", "V2", "V3", "V4", "V5", "V6"};

    /**
     * 等级成长值门槛数组
     * LEVEL_VALUES[1] = 0 (V1需要0成长值)
     * LEVEL_VALUES[2] = 500 (V2需要500成长值)
     */
    private static final int[] LEVEL_VALUES = {0, 500, 2000, 5000, 15000, 50000};

    /**
     * 微信一键登录注册
     *
     * 一键登录流程:
     * 1. 小程序调用 wx.login() 获取 code
     * 2. 将 code 发送到后端 /member/wxlogin
     * 3. 后端用 code 换取 openid
     * 4. 根据 openid 查询会员,不存在则自动创建
     * 5. 返回 JWT Token
     *
     * @param dto 登录请求参数 (只需code)
     * @return 登录结果 (Token和会员信息)
     */
    @Override
    @Transactional
    public LoginVO login(LoginDTO dto) {
        // Step 1: 用 code 换取 openid
        WechatUtils.WechatSessionResult wechatResult;
        try {
            wechatResult = wechatUtils.code2Session(dto.getCode());
        } catch (Exception e) {
            log.error("微信登录失败: code={}, error={}", dto.getCode(), e.getMessage());
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }

        // Step 2: 查询或创建会员
        Member member = memberMapper.selectByOpenid(wechatResult.getOpenid());
        boolean isNewMember = (member == null);

        if (member == null) {
            // 不存在则创建新会员
            member = new Member();
            member.setOpenid(wechatResult.getOpenid());
            member.setUnionid(wechatResult.getUnionid());
            member.setNickname("微信用户");
            member.setAvatar("/static/images/mine/default.png");
            member.setMemberLevel(1); // 默认V1等级
            member.setPointNum(0);
            member.setCouponNum(0);
            member.setBalance(BigDecimal.ZERO);
            member.setGiftBalance(BigDecimal.ZERO);
            member.setCurrentValue(0);
            member.setMemberOrigin("wechat");
            member.setLastLoginTime(LocalDateTime.now());
            memberMapper.insert(member);
            log.info("【新会员注册】openid={}, customerId={}", wechatResult.getOpenid(), member.getCustomerId());
        }

        // Step 3: 更新登录时间
        memberMapper.updateLoginTime(member.getCustomerId());

        // Step 4: 生成 JWT Token
        String token = jwtUtils.generateToken(member.getCustomerId(), member.getOpenid());

        // Step 5: 构建返回结果
        LoginVO vo = new LoginVO();
        vo.setToken(token);
        vo.setTokenType("Bearer");
        vo.setExpiresIn(604800); // 7天有效期
        vo.setCustomerId(member.getCustomerId());
        vo.setNickname(member.getNickname());
        vo.setAvatar(member.getAvatar());
        vo.setMobilePhone(member.getMobilePhone() != null ? member.getMobilePhone() : "");
        vo.setIsNewMember(isNewMember);

        log.info("【会员登录】customerId={}, isNew={}", member.getCustomerId(), isNewMember);
        return vo;
    }

    /**
     * 获取会员信息
     *
     * @param memberId 会员ID
     * @return 会员信息VO
     */
    @Override
    public MemberInfoVO getMemberInfo(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        if (member == null) {
            throw new BusinessException(ResultCode.MEMBER_NOT_FOUND);
        }

        // 构建会员信息VO
        MemberInfoVO vo = new MemberInfoVO();
        vo.setCustomerId(member.getCustomerId());
        vo.setNickname(member.getNickname());
        vo.setAvatar(member.getAvatar());
        vo.setMobilePhone(member.getMobilePhone());
        vo.setGender(member.getGender());
        vo.setCardNo(member.getCardNo());
        vo.setCardName(LEVEL_NAMES[member.getMemberLevel()]);
        vo.setMemberLevel(member.getMemberLevel());
        vo.setMemberLevelName("VIP" + member.getMemberLevel());
        vo.setPointNum(member.getPointNum());
        vo.setCouponNum(member.getCouponNum());
        vo.setBalance(member.getBalance());
        vo.setGiftBalance(member.getGiftBalance());
        vo.setCurrentValue(member.getCurrentValue());

        // 计算距离下一级所需成长值
        int nextLevelValue = LEVEL_VALUES[member.getMemberLevel()];
        vo.setNeedValue(Math.max(0, nextLevelValue - member.getCurrentValue()));

        vo.setBirthday(member.getBirthday() != null ? member.getBirthday().toString() : null);
        vo.setMemberOrigin(member.getMemberOrigin());
        vo.setExpenseAmount(member.getExpenseAmount());
        vo.setLevel(member.getMemberLevel());
        vo.setCreatedAt(member.getCreatedAt() != null ? member.getCreatedAt().toEpochSecond(ZoneOffset.UTC) : null);

        return vo;
    }

    /**
     * 更新会员信息
     *
     * @param memberId 会员ID
     * @param nickname 昵称
     * @param avatar 头像
     * @param gender 性别
     * @param birthday 生日
     */
    @Override
    @Transactional
    public void updateMemberInfo(Long memberId, UpdateMemberDTO dto) {
        Member member = new Member();
        member.setCustomerId(memberId);
        // 只更新非空字段
        if (dto.getNickname() != null) member.setNickname(dto.getNickname());
        if (dto.getAvatar() != null) member.setAvatar(dto.getAvatar());
        if (dto.getGender() != null) member.setGender(dto.getGender());
        if (dto.getBirthday() != null) {
            String birthdayStr = dto.getBirthday().replace("T", " ");
            LocalDateTime birthday = LocalDateTime.parse(birthdayStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            member.setBirthday(birthday);
            log.info("【会员生日更新】memberId={}, birthday={}", memberId, member.getBirthday());
        }

        memberMapper.updateById(member);
    }
}
