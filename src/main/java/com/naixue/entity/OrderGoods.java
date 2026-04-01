package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品实体类
 *
 * 对应数据库表: order_goods
 *
 * 记录订单中的单个商品信息,包括:
 * - 商品基础信息: goodsId, goodsName, price
 * - 购买数量: number
 * - 小计金额: amount (price * number)
 * - 规格属性: property (如: "去冰,标准糖")
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class OrderGoods implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单商品ID (主键,自增) */
    private Long id;

    /** 订单ID (关联orders表的id) */
    private Long orderId;

    /** 商品ID (关联goods表的id) */
    private Long goodsId;

    /** 商品名称 (冗余存储,防止商品下架后名称丢失) */
    private String goodsName;

    /** 单价 */
    private BigDecimal price;

    /** 购买数量 */
    private Integer number;

    /** 小计金额 (price * number) */
    private BigDecimal amount;

    /**
     * 规格属性
     * 格式: "甜度,温度" 或 "大杯,去冰,正常糖"
     */
    private String property;

    /** 商品图片URL */
    private String image;
}
