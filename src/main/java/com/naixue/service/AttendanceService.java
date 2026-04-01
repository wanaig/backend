package com.naixue.service;

import com.naixue.entity.Attendance;
import com.naixue.entity.AttendanceRule;
import com.naixue.vo.AttendanceTodayVO;
import com.naixue.vo.SignResultVO;
import java.util.List;

/**
 * 签到服务接口
 *
 * 定义签到相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public interface AttendanceService {

    /**
     * 获取签到规则
     *
     * 返回连续签到的奖励规则
     *
     * @return 签到规则列表
     */
    List<AttendanceRule> getAttendanceRules();

    /**
     * 获取今日签到状态
     *
     * @param memberId 会员ID
     * @return 今日签到状态
     */
    AttendanceTodayVO getTodayStatus(Long memberId);

    /**
     * 签到
     *
     * 流程:
     * 1. 检查今日是否已签到
     * 2. 计算连续签到天数
     * 3. 根据规则计算奖励积分
     * 4. 保存签到记录
     * 5. 增加会员积分
     * 6. 记录积分流水
     *
     * @param memberId 会员ID
     * @return 签到结果
     */
    SignResultVO sign(Long memberId);

    /**
     * 获取签到历史
     *
     * @param memberId 会员ID
     * @return 签到记录列表
     */
    List<Attendance> getSignHistory(Long memberId);
}
