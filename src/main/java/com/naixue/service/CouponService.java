package com.naixue.service;

import com.naixue.entity.Coupon;
import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券服务接口
 *
 * 定义优惠券相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public interface CouponService {

    /**
     * 获取优惠券列表
     *
     * @param memberId 会员ID
     * @param type 类型筛选 (all-全部)
     * @return 优惠券列表
     */
    List<Coupon> getCouponList(Long memberId, String type);

    /**
     * 兑换优惠券
     *
     * 根据兑换码兑换优惠券
     *
     * @param memberId 会员ID
     * @param code 兑换码
     */
    void exchangeCoupon(Long memberId, String code);

    /**
     * 获取可用优惠券
     *
     * 根据订单金额筛选可用优惠券
     *
     * @param memberId 会员ID
     * @param amount 订单金额
     * @return 可用优惠券列表
     */
    List<Coupon> getAvailableCoupons(Long memberId, BigDecimal amount);
}
