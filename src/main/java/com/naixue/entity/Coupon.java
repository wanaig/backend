package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 优惠券实体类
 *
 * 对应数据库表: coupon 和 member_coupon
 *
 * 优惠券类型:
 * - discount_unit = 1: 按金额优惠 (discount_amount为具体金额)
 * - discount_unit = 2: 按折扣优惠 (discount_amount为折扣率,如0.8表示8折)
 *
 * couponType (优惠券类型):
 * - 1: 茶饮券
 * - 2: 酒屋券
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 优惠券ID (主键,自增) */
    private Long id;

    /** 优惠券标识ID (用于业务关联) */
    private String couponId;

    /** 优惠券名称/标题 */
    private String title;

    /** 优惠券使用说明 (HTML格式) */
    private String couponExplain;

    /** 优惠券图片URL */
    private String imageUrl;

    /**
     * 优惠金额/折扣率
     * - discount_unit=1时: 表示减免金额(如5.00表示减5元)
     * - discount_unit=2时: 表示折扣率(如0.80表示8折)
     */
    private BigDecimal discountAmount;

    /**
     * 优惠单位: 1-金额 2-折扣
     */
    private Integer discountUnit;

    /** 使用门槛 (满指定金额可用,为0表示无门槛) */
    private String beginAt;

    /** 结束时间 */
    private String endAt;

    /**
     * 使用时间范围
     * 格式: [{"begin":"00:00:00","end":"23:59:59"}]
     */
    private List<Map<String, String>> useTimeScope;

    /**
     * 优惠券类型: 1-茶饮券 2-酒屋券
     */
    private Integer couponType;

    /** 商家名称 */
    private String sellerName;
}
