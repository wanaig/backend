package com.naixue.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 今日签到状态响应VO
 *
 * 用于返回用户今日签到状态和签到日历信息
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class AttendanceTodayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 今日是否已签到
     * 0-未签到
     * 1-已签到
     */
    private Integer isAttendance;

    /** 总会话积分 */
    private Integer totalPoints;

    /** 今日签到可得积分 */
    private Integer attendancePoints;

    /** 签到类别 */
    private Integer attendanceCategory;

    /** 连续签到天数 */
    private Integer attendanceContinuityDay;

    /** 签到日历列表 (7天) */
    private List<SignRecord> list;

    /**
     * 签到记录内部类
     *
     * @author naixue-backend
     */
    @Data
    public static class SignRecord implements Serializable {
        private static final long serialVersionUID = 1L;

        /** 签到天数 (1-7) */
        private Integer attendanceDay;

        /** 奖励优惠券名称 */
        private String couponName;

        /** 奖励优惠券ID */
        private String couponId;

        /**
         * 签到状态
         * 0-未签到
         * 1-已签到
         */
        private Integer status;
    }
}
