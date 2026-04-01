package com.naixue.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * 签到结果响应VO
 *
 * 签到成功后返回的积分奖励信息
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class SignResultVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 获得积分数量 */
    private Integer points;

    /** 连续签到天数 */
    private Integer continuousDay;

    /** 总会话积分 */
    private Integer totalPoints;
}
