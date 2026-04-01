package com.naixue.dto;

import lombok.Data;
import java.util.List;

/**
 * 创建订单请求DTO
 *
 * 用于接收小程序创建订单的请求参数
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class CreateOrderDTO {

    /** 门店ID */
    private Long storeId;

    /**
     * 订单类型
     * 1-自取
     * 2-外卖
     */
    private Integer typeCate;

    /** 收货地址ID (外卖时必填) */
    private Long addressId;

    /** 订单备注 */
    private String remark;

    /** 使用的优惠券ID */
    private Long couponId;

    /** 商品列表 */
    private List<GoodsItem> goodsList;

    /**
     * 商品项内部类
     * 表示订单中的单个商品
     *
     * @author naixue-backend
     */
    @Data
    public static class GoodsItem {

        /** 商品ID */
        private Long goodsId;

        /** 购买数量 */
        private Integer number;

        /**
         * 规格属性
         * 格式: "甜度,温度" 如 "去冰,标准糖"
         */
        private String property;
    }
}
