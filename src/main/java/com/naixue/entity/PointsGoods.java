package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 积分商品实体类
 *
 * 对应数据库表: points_goods
 *
 * 积分商品是可用积分兑换的商品/优惠券
 *
 * goodsType (商品类型):
 * - 1: 实物商品
 * - 2: 电子券 (优惠券)
 * - 3: 周边礼品
 *
 * exchangeType (兑换类型):
 * - 1: 纯积分兑换
 * - 2: 积分+金额混合兑换
 *
 * category (分类):
 * - "奈雪好券": 优惠券类
 * - "奈雪好物": 实物类
 * - "奈雪联名": 联名款
 * - "奈雪好茶": 茶叶类
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class PointsGoods implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 商品ID (主键,自增) */
    private Long id;

    /** 商品名称 */
    private String goodsName;

    /** 积分价格 (兑换所需积分) */
    private Integer pointsPrice;

    /** 商品金额 (如果需要额外支付金额) */
    private BigDecimal amount;

    /**
     * 兑换类型: 1-纯积分 2-积分+金额
     */
    private Integer exchangeType;

    /** 库存数量 */
    private Integer goodsStock;

    /** 已兑换数量 */
    private Integer exchangedNum;

    /** 商品图片列表 */
    private List<String> img;

    /** 兑换说明 (HTML格式) */
    private String exchangeDesc;

    /**
     * 商品类型: 1-实物 2-电子券 3-周边
     */
    private Integer goodsType;
}
