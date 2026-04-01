package com.naixue.dto;

import lombok.Data;

/**
 * 积分兑换请求DTO
 *
 * 用于接收用户积分兑换商品的请求参数
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class PointsExchangeDTO {

    /** 积分商品ID */
    private Long goodsId;

    /** 兑换数量 */
    private Integer num;
}
