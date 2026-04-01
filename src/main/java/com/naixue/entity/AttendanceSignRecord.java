package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 签到记录展示实体类
 *
 * 非数据库表,用于前端展示签到日历
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class AttendanceSignRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 签到天数序号 (1-7) */
    private Integer attendanceDay;

    /** 奖励优惠券名称 */
    private String couponName;

    /** 奖励优惠券ID */
    private String couponId;

    /**
     * 签到状态
     * 0-未签到 1-已签到
     */
    private Integer status;
}
