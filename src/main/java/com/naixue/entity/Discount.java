package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单优惠信息实体类
 *
 * 非数据库表,用于返回订单优惠明细
 *
 * 包含订单使用的优惠券、满减活动等优惠信息
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class Discount implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 优惠名称
     * 例如: "茶饮满二赠一券", "新人立减10元"
     */
    private String name;

    /** 优惠金额 */
    private BigDecimal amount;

    /**
     * 优惠方式/来源
     * - "coupon": 优惠券
     * - "discount": 满减活动
     * - "points": 积分抵扣
     */
    private String method;
}
