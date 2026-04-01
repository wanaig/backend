package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 签到规则实体类
 *
 * 非数据库表,定义签到奖励规则
 *
 * 签到规则说明:
 * - 第1天签到: 1积分
 * - 第2天签到: 1积分
 * - 第3天签到: 2积分
 * - 第4天签到: 3积分
 * - 第5天签到: 5积分
 * - 第6天签到: 5积分
 * - 第7天签到: 10积分 (周奖励)
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class AttendanceRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 签到奖励积分数 */
    private Integer points;

    /** 签到天数 (第几天) */
    private Integer dayName;

    /**
     * 是否完成标记
     * 0-未完成 1-已完成
     * 用于前端展示签到进度
     */
    private Integer isDay;
}
