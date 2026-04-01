package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员优惠券实体类
 *
 * 对应数据库表: member_coupon
 *
 * 记录会员拥有的优惠券信息
 *
 * status (状态):
 * - 0: 未使用
 * - 1: 已使用
 * - 2: 已过期
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class MemberCoupon implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 会员优惠券ID (主键,自增) */
    private Long id;

    /** 会员ID */
    private Long memberId;

    /** 优惠券ID (关联coupon表的id) */
    private Long couponId;

    /**
     * 状态: 0-未使用 1-已使用 2-已过期
     */
    private Integer status;

    /** 过期时间 */
    private LocalDateTime endAt;

    /** 领取时间 */
    private LocalDateTime createdAt;
}
