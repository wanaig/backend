package com.naixue.dto;

import lombok.Data;

/**
 * 储值请求DTO
 *
 * 用于接收用户储值的请求参数
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class RechargeDTO {

    /** 储值卡ID */
    private Long cardId;

    /**
     * 支付方式
     * "wechat" - 微信支付
     * "balance" - 余额支付
     */
    private String payMode;
}
