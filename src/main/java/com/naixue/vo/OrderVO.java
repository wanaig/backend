package com.naixue.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单响应VO
 *
 * 用于返回订单详情和订单列表
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class OrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long id;

    /** 订单号 */
    private String orderNo;

    /** 订单状态码 */
    private Integer status;

    /** 状态文本 */
    private String statusText;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 实付金额 */
    private BigDecimal amount;

    /** 商品数量 */
    private Integer goodsNum;

    /** 支付方式 */
    private String payMode;

    /** 创建时间 (时间戳秒) */
    private Long createdAt;

    /** 支付时间 (时间戳秒) */
    private Long payedAt;

    /** 完成时间 */
    private String completedTime;

    /** 制作完成时间 */
    private String productionedTime;

    /** 备注 */
    private String postscript;

    /** 取餐号 */
    private String sortNum;

    /** 订单类型: 1-自取 2-外卖 */
    private Integer typeCate;

    /** 优惠券金额 */
    private BigDecimal couponAmount;

    /** 门店信息 */
    private StoreVO store;

    /** 商品列表 */
    private List<OrderGoodsVO> goods;

    /** 优惠信息列表 */
    private List<DiscountVO> discount;

    /**
     * 门店信息内部类
     *
     * @author naixue-backend
     */
    @Data
    public static class StoreVO implements Serializable {
        private static final long serialVersionUID = 1L;

        /** 门店名称 */
        private String name;

        /** 门店地址 */
        private String address;

        /** 门店电话 */
        private String mobile;
    }

    /**
     * 订单商品信息内部类
     *
     * @author naixue-backend
     */
    @Data
    public static class OrderGoodsVO implements Serializable {
        private static final long serialVersionUID = 1L;

        /** 商品名称 */
        private String name;

        /** 数量 */
        private Integer number;

        /** 单价 */
        private String price;

        /** 小计金额 */
        private String amount;

        /** 规格属性 */
        private String property;

        /** 商品图片 */
        private String image;
    }

    /**
     * 优惠信息内部类
     *
     * @author naixue-backend
     */
    @Data
    public static class DiscountVO implements Serializable {
        private static final long serialVersionUID = 1L;

        /** 优惠名称 */
        private String name;

        /** 优惠金额 */
        private String amount;

        /** 优惠方式 */
        private String method;
    }
}
