package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 储值卡实体类
 *
 * 对应数据库表: recharge_card
 *
 * 储值卡是一种预付费卡,用户充值后可获得相应余额
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class RechargeCard implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 储值卡ID (主键,自增) */
    private Long id;

    /** 储值卡名称 (如: "100元储值卡") */
    private String name;

    /** 储值面值 */
    private BigDecimal value;

    /** 售价 (通常与面值相同,部分活动可能有折扣) */
    private BigDecimal sellPrice;

    /** 储值卡说明 */
    private String desc;

    /** 储值卡图片 */
    private String image;

    /** 销量 */
    private Integer sales;

    /** 状态: 0-禁用 1-启用 */
    private Integer status;

    /** 赠送礼品列表 (非数据库字段) */
    private List<Object> gifts;
}
