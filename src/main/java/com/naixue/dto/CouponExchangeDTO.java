package com.naixue.dto;

import lombok.Data;

/**
 * 优惠券兑换请求DTO
 *
 * 用于接收用户兑换优惠券的请求参数
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class CouponExchangeDTO {

    /**
     * 兑换码
     * 用户输入的优惠券兑换码
     * 兑换码通常印刷在实体卡上或通过活动获取
     */
    private String code;
}
