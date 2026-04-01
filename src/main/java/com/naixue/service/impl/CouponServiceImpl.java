package com.naixue.service.impl;

import com.naixue.common.ResultCode;
import com.naixue.entity.Coupon;
import com.naixue.exception.BusinessException;
import com.naixue.mapper.CouponMapper;
import com.naixue.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券服务实现类
 *
 * 实现CouponService接口,提供优惠券相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    /** 优惠券Mapper */
    private final CouponMapper couponMapper;

    /**
     * 获取优惠券列表
     *
     * @param memberId 会员ID
     * @param type 类型筛选 ("all"表示全部)
     * @return 优惠券列表
     */
    @Override
    public List<Coupon> getCouponList(Long memberId, String type) {
        List<Coupon> coupons;
        if ("all".equals(type)) {
            coupons = couponMapper.selectAllByMemberId(memberId);
        } else {
            coupons = couponMapper.selectAvailableByMemberId(memberId);
        }
        return coupons;
    }

    /**
     * 兑换优惠券
     *
     * 根据兑换码查找优惠券并添加到会员账户
     * 兑换后优惠券30天后过期
     *
     * @param memberId 会员ID
     * @param code 兑换码
     */
    @Override
    @Transactional
    public void exchangeCoupon(Long memberId, String code) {
        List<Coupon> allCoupons = couponMapper.selectAll();
        Coupon targetCoupon = null;

        // 根据兑换码查找优惠券
        for (Coupon coupon : allCoupons) {
            if (coupon.getTitle().contains(code) || coupon.getCouponExplain().contains(code)) {
                targetCoupon = coupon;
                break;
            }
        }

        if (targetCoupon == null) {
            throw new BusinessException(ResultCode.COUPON_NOT_AVAILABLE);
        }

        // 检查是否已领取过
        int count = couponMapper.countAvailableCoupon(memberId, targetCoupon.getId());
        if (count > 0) {
            throw new BusinessException(ResultCode.COUPON_NOT_AVAILABLE);
        }

        // 领取优惠券,30天后过期
        LocalDateTime endAt = LocalDateTime.now().plusDays(30);
        couponMapper.insertMemberCoupon(memberId, targetCoupon.getId(), endAt);

        log.info("兑换优惠券: memberId={}, couponId={}", memberId, targetCoupon.getId());
    }

    /**
     * 获取可用优惠券
     *
     * 根据订单金额筛选可用优惠券
     *
     * @param memberId 会员ID
     * @param amount 订单金额
     * @return 可用优惠券列表
     */
    @Override
    public List<Coupon> getAvailableCoupons(Long memberId, BigDecimal amount) {
        List<Coupon> coupons = couponMapper.selectAvailableByMemberId(memberId);
        return coupons.stream().filter(c -> {
            // 检查是否有门槛金额
            if (c.getDiscountAmount() != null && c.getDiscountAmount().compareTo(BigDecimal.ZERO) > 0) {
                return amount.compareTo(c.getDiscountAmount()) >= 0;
            }
            return true;
        }).toList();
    }
}
