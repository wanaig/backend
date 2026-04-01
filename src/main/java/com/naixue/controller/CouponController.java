package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.dto.CouponExchangeDTO;
import com.naixue.entity.Coupon;
import com.naixue.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券控制器
 *
 * 提供优惠券相关的API接口
 *
 * 接口列表:
 * - GET /coupon - 获取优惠券列表
 * - POST /coupon/exchange - 兑换优惠券
 * - GET /coupon/available - 获取可用优惠券
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    /** 优惠券服务 */
    private final CouponService couponService;

    /**
     * 获取优惠券列表
     *
     * GET /coupon
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param type 类型筛选 (all-全部)
     * @return 优惠券列表
     */
    @GetMapping
    public Result<List<Coupon>> getCouponList(
            @RequestAttribute Long memberId,
            @RequestParam(required = false, defaultValue = "all") String type) {
        List<Coupon> coupons = couponService.getCouponList(memberId, type);
        return Result.success(coupons);
    }

    /**
     * 兑换优惠券
     *
     * POST /coupon/exchange
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param dto 兑换参数 (包含兑换码)
     * @return 成功标识
     */
    @PostMapping("/exchange")
    public Result<Void> exchangeCoupon(
            @RequestAttribute Long memberId,
            @RequestBody CouponExchangeDTO dto) {
        couponService.exchangeCoupon(memberId, dto.getCode());
        return Result.success();
    }

    /**
     * 获取可用优惠券
     *
     * GET /coupon/available
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param amount 订单金额
     * @return 可用优惠券列表
     */
    @GetMapping("/available")
    public Result<List<Coupon>> getAvailableCoupons(
            @RequestAttribute Long memberId,
            @RequestParam BigDecimal amount) {
        List<Coupon> coupons = couponService.getAvailableCoupons(memberId, amount);
        return Result.success(coupons);
    }
}
