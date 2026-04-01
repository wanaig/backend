package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 积分流水实体类
 *
 * 对应数据库表: points_flow
 *
 * 记录会员积分的变动明细
 *
 * changeType (变动类型):
 * - 1: 增加
 * - 2: 减少
 *
 * sourceType (来源类型):
 * - 1: 订单获得
 * - 2: 退款扣除
 * - 3: 积分兑换
 * - 4: 活动奖励
 * - 5: 签到奖励
 * - 6: 储值赠送
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class PointsFlow implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 流水ID (主键,自增) */
    private Long id;

    /** 会员ID */
    private Long memberId;

    /**
     * 变动类型: 1-增加 2-减少
     */
    private Integer changeType;

    /** 变动数量 (正数) */
    private Integer changeNum;

    /** 变动原因 */
    private String reason;

    /**
     * 来源类型:
     * 1-订单, 2-退款, 3-兑换, 4-活动, 5-签到, 6-储值
     */
    private Integer sourceType;

    /** 商家名称 */
    private String sellerName;

    /** 创建时间 */
    private LocalDateTime createdAt;
}
