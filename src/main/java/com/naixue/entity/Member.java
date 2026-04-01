package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 会员实体类
 *
 * 对应数据库表: member
 *
 * 会员信息包含:
 * - 基本信息: openid, unionid, nickname, avatar, mobilePhone, gender
 * - 会员等级: memberLevel (1-6对应V1-V6), cardName, currentValue
 * - 资产信息: pointNum (积分), balance (余额), giftBalance (礼品卡余额)
 * - 扩展信息: birthday, memberOrigin (来源), lastLoginTime
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 会员ID (主键,自增) */
    private Long customerId;

    /** 微信OpenID (唯一索引) */
    private String openid;

    /** 微信UnionID */
    private String unionid;

    /** 会员昵称 */
    private String nickname;

    /** 会员头像URL */
    private String avatar;

    /** 手机号 */
    private String mobilePhone;

    /** 性别: 0-女 1-男 */
    private Integer gender;

    /** 会员卡号 */
    private String cardNo;

    /**
     * 会员等级: 1-V1, 2-V2, 3-V3, 4-V4, 5-V5, 6-V6
     * 等级成长值要求:
     * - V1: 0, V2: 500, V3: 2000, V4: 5000, V5: 15000, V6: 50000
     */
    private Integer memberLevel;

    /** 用户名 */
    private String username;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 区县 */
    private String district;

    /** 门店ID */
    private Long storeId;

    /** 开卡日期 */
    private LocalDateTime openingCardDate;

    /** 会员卡图片URL */
    private String cardUrl;

    /** 积分数量 */
    private Integer pointNum;

    /** 优惠券数量 */
    private Integer couponNum;

    /** 余额 (储值余额) */
    private BigDecimal balance;

    /** 礼品卡余额 */
    private BigDecimal giftBalance;

    /** 累计消费金额 */
    private BigDecimal expenseAmount;

    /** 当前成长值 */
    private Integer currentValue;

    /** 生日 */
    private LocalDateTime birthday;

    /**
     * 会员来源
     * 可能的值: "wechat" (微信), "miniapp" (小程序), "app" (APP)
     */
    private String memberOrigin;

    /** 最后登录时间 */
    private LocalDateTime lastLoginTime;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;

    /** 删除标记: 0-未删除 1-已删除 */
    private Integer deleted;
}
