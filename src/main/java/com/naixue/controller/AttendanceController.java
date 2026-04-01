package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.entity.Attendance;
import com.naixue.entity.AttendanceRule;
import com.naixue.service.AttendanceService;
import com.naixue.vo.AttendanceTodayVO;
import com.naixue.vo.SignResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 签到控制器
 *
 * 提供签到相关的API接口
 *
 * 接口列表:
 * - GET /attendance/rule - 获取签到规则
 * - GET /attendance/today - 获取今日签到状态
 * - POST /attendance/sign - 签到
 * - GET /attendance/history - 获取签到历史
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    /** 签到服务 */
    private final AttendanceService attendanceService;

    /**
     * 获取签到规则
     *
     * GET /attendance/rule
     * 需要登录 (JWT Token)
     *
     * @return 签到规则列表
     */
    @GetMapping("/rule")
    public Result<List<AttendanceRule>> getAttendanceRules() {
        List<AttendanceRule> rules = attendanceService.getAttendanceRules();
        return Result.success(rules);
    }

    /**
     * 获取今日签到状态
     *
     * GET /attendance/today
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @return 今日签到状态
     */
    @GetMapping("/today")
    public Result<AttendanceTodayVO> getTodayStatus(@RequestAttribute Long memberId) {
        AttendanceTodayVO status = attendanceService.getTodayStatus(memberId);
        return Result.success(status);
    }

    /**
     * 签到
     *
     * POST /attendance/sign
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @return 签到结果
     */
    @PostMapping("/sign")
    public Result<SignResultVO> sign(@RequestAttribute Long memberId) {
        SignResultVO result = attendanceService.sign(memberId);
        return Result.success(result);
    }

    /**
     * 获取签到历史
     *
     * GET /attendance/history
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @return 签到历史
     */
    @GetMapping("/history")
    public Result<List<Attendance>> getSignHistory(@RequestAttribute Long memberId) {
        List<Attendance> history = attendanceService.getSignHistory(memberId);
        return Result.success(history);
    }
}
