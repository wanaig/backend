package com.naixue.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码枚举类
 *
 * 定义了系统使用的所有响应码
 *
 * 响应码范围:
 * - 0: 成功
 * - 1001-1999: 参数错误
 * - 2001-2999: 认证错误
 * - 3001-3999: 会员错误
 * - 4001-4999: 订单错误
 * - 5001-5999: 商品错误
 * - 6001-6999: 地址错误
 * - 9001-9999: 系统错误
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /** 成功 */
    SUCCESS(0, "success"),

    // ========== 参数错误 1001-1999 ==========

    /** 参数错误 */
    PARAM_ERROR(1001, "参数错误"),

    // ========== 认证错误 2001-2999 ==========

    /** 未登录 */
    NOT_LOGIN(2001, "请先登录"),

    /** 登录已过期 */
    TOKEN_EXPIRED(2002, "登录已过期"),

    // ========== 会员错误 3001-3999 ==========

    /** 会员不存在 */
    MEMBER_NOT_FOUND(3001, "会员不存在"),

    /** 积分不足 */
    POINTS_NOT_ENOUGH(3002, "积分不足"),

    /** 余额不足 */
    BALANCE_NOT_ENOUGH(3004, "余额不足"),

    /** 优惠券不可用 */
    COUPON_NOT_AVAILABLE(3003, "优惠券不可用"),

    // ========== 订单错误 4001-4999 ==========

    /** 订单不存在 */
    ORDER_NOT_FOUND(4001, "订单不存在"),

    /** 订单状态错误 */
    ORDER_STATUS_ERROR(4002, "订单状态错误"),

    /** 库存不足 */
    STOCK_NOT_ENOUGH(4003, "库存不足"),

    // ========== 商品错误 5001-5999 ==========

    /** 商品不存在 */
    GOODS_NOT_FOUND(5001, "商品不存在"),

    /** 商品已下架 */
    GOODS_OFFLINE(5002, "商品已下架"),

    // ========== 地址错误 6001-6999 ==========

    /** 地址不存在 */
    ADDRESS_NOT_FOUND(6001, "地址不存在"),

    // ========== 系统错误 9001-9999 ==========

    /** 系统错误 */
    SYSTEM_ERROR(9001, "系统错误");

    /** 响应码 */
    private final Integer code;

    /** 响应消息 */
    private final String message;
}
