package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 签到记录实体类
 *
 * 对应数据库表: attendance
 *
 * 记录用户的签到明细,包括:
 * - 签到日期: date
 * - 获得积分: attendancePoint
 * - 连续签到天数: rewardDays
 *
 * 使用(member_id, date)联合唯一索引,防止重复签到
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 签到记录ID (主键,自增) */
    private Long id;

    /** 会员ID */
    private Long memberId;

    /** 签到日期 (格式: yyyy-MM-dd) */
    private String date;

    /** 获得积分数量 */
    private Integer attendancePoint;

    /** 连续签到天数 */
    private Integer rewardDays;

    /** 签到门店ID */
    private Long storeId;

    /** 创建时间 */
    private LocalDateTime createdAt;
}
