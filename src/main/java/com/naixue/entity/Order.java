package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单实体类
 *
 * 对应数据库表: orders
 *
 * 订单信息包含:
 * - 基本信息: orderNo (订单号), memberId, storeId
 * - 订单类型: typeCate (1-自取 2-外卖)
 * - 金额信息: totalAmount (总金额), amount (实付金额), couponAmount (优惠券金额)
 * - 状态信息: status (订单状态), payMode (支付方式)
 * - 关联数据: store (门店信息), goodsList (商品列表), discountList (优惠信息)
 *
 * 订单状态说明:
 * - 0: 待支付
 * - 1: 已支付
 * - 2: 制作中
 * - 3: 已完成
 * - 4: 已取消
 * - 5: 退款中
 * - 6: 已退款
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单ID (主键,自增) */
    private Long id;

    /** 订单号 (唯一) */
    private String orderNo;

    /** 会员ID (下单用户) */
    private Long memberId;

    /** 门店ID */
    private Long storeId;

    /**
     * 订单类型: 1-自取 2-外卖
     */
    private Integer typeCate;

    /**
     * 订单状态:
     * 0-待支付, 1-已支付, 2-制作中, 3-已完成, 4-已取消, 5-退款中, 6-已退款
     */
    private Integer status;

    /** 订单总金额 (原价) */
    private BigDecimal totalAmount;

    /** 实付金额 (优惠后) */
    private BigDecimal amount;

    /** 商品总数量 */
    private Integer goodsNum;

    /** 支付方式: "微信支付", "余额支付"等 */
    private String payMode;

    /** 支付时间 */
    private LocalDateTime payedAt;

    /** 取餐号 */
    private String sortNum;

    /** 订单备注 */
    private String postscript;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;

    /** 删除标记: 0-未删除 1-已删除 */
    private Integer deleted;

    // ========== 关联数据 (非数据库字段) ==========

    /** 关联门店信息 */
    private Store store;

    /** 订单商品列表 */
    private List<OrderGoods> goodsList;

    /** 订单优惠信息列表 */
    private List<Discount> discountList;
}
