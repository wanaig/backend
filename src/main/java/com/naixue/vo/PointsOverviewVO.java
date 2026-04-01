package com.naixue.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * 积分概况响应VO
 *
 * 用于返回会员积分的汇总信息
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class PointsOverviewVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 总会话积分 */
    private Integer totalPoints;

    /** 永久积分 (永不过期) */
    private Integer foreverPoints;

    /** 即将过期积分 (30天内过期) */
    private Integer soonExpiredPoints;

    /** 过期时间 (格式: yyyy-MM-dd) */
    private String expiredTime;
}
