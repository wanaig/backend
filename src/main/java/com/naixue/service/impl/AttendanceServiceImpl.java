package com.naixue.service.impl;

import com.naixue.common.ResultCode;
import com.naixue.entity.Attendance;
import com.naixue.entity.AttendanceRule;
import com.naixue.entity.Member;
import com.naixue.entity.PointsFlow;
import com.naixue.exception.BusinessException;
import com.naixue.mapper.AttendanceMapper;
import com.naixue.mapper.MemberMapper;
import com.naixue.mapper.PointsMapper;
import com.naixue.service.AttendanceService;
import com.naixue.vo.AttendanceTodayVO;
import com.naixue.vo.SignResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 签到服务实现类
 *
 * 实现AttendanceService接口,提供签到相关的业务逻辑
 *
 * 签到奖励规则:
 * - 第1天: 1积分
 * - 第2天: 1积分
 * - 第3天: 2积分
 * - 第4天: 3积分
 * - 第5天: 5积分
 * - 第6天: 5积分
 * - 第7天: 10积分 (周奖励)
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    /** 签到Mapper */
    private final AttendanceMapper attendanceMapper;

    /** 会员Mapper */
    private final MemberMapper memberMapper;

    /** 积分流水Mapper */
    private final PointsMapper pointsMapper;

    /**
     * 签到规则列表
     */
    private static final List<AttendanceRule> RULES = new ArrayList<>();

    // 初始化签到规则
    static {
        RULES.add(createRule(1, 1, 1));
        RULES.add(createRule(1, 2, 0));
        RULES.add(createRule(2, 3, 0));
        RULES.add(createRule(3, 4, 0));
        RULES.add(createRule(5, 5, 0));
        RULES.add(createRule(5, 6, 0));
        RULES.add(createRule(10, 7, 0));
    }

    /**
     * 创建签到规则
     */
    private static AttendanceRule createRule(int points, int dayName, int isDay) {
        AttendanceRule rule = new AttendanceRule();
        rule.setPoints(points);
        rule.setDayName(dayName);
        rule.setIsDay(isDay);
        return rule;
    }

    /**
     * 获取签到规则
     *
     * @return 签到规则列表
     */
    @Override
    public List<AttendanceRule> getAttendanceRules() {
        return RULES;
    }

    /**
     * 获取今日签到状态
     *
     * @param memberId 会员ID
     * @return 今日签到状态
     */
    @Override
    public AttendanceTodayVO getTodayStatus(Long memberId) {
        // 获取今天的日期
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 查询今日签到记录
        Attendance todayAttendance = attendanceMapper.selectByMemberIdAndDate(memberId, today);

        AttendanceTodayVO vo = new AttendanceTodayVO();
        vo.setIsAttendance(todayAttendance != null ? 1 : 0);

        // 获取会员信息
        Member member = memberMapper.selectById(memberId);
        vo.setTotalPoints(member != null ? member.getPointNum() : 0);

        // 计算连续签到天数
        List<Attendance> recentAttendances = attendanceMapper.selectRecentByMemberId(memberId);
        int continuityDay = 0;
        for (Attendance attendance : recentAttendances) {
            if (attendance.getRewardDays() != null && attendance.getRewardDays() > continuityDay) {
                continuityDay = attendance.getRewardDays();
            }
        }
        vo.setAttendanceContinuityDay(continuityDay);
        vo.setAttendancePoints(1);
        vo.setAttendanceCategory(1);

        // 构建签到日历
        List<AttendanceTodayVO.SignRecord> records = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            AttendanceTodayVO.SignRecord record = new AttendanceTodayVO.SignRecord();
            record.setAttendanceDay(i);
            record.setCouponName("");
            record.setCouponId("0");
            record.setStatus(0);
            records.add(record);
        }
        vo.setList(records);

        return vo;
    }

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
    @Override
    @Transactional
    public SignResultVO sign(Long memberId) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 检查今日是否已签到
        Attendance existing = attendanceMapper.selectByMemberIdAndDate(memberId, today);
        if (existing != null) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }

        // 计算连续签到天数
        int continuityDay = 1;
        List<Attendance> recentAttendances = attendanceMapper.selectRecentByMemberId(memberId);
        if (!recentAttendances.isEmpty()) {
            Attendance lastAttendance = recentAttendances.get(0);
            String lastDate = lastAttendance.getDate();
            LocalDate last = LocalDate.parse(lastDate);
            LocalDate yesterday = LocalDate.now().minusDays(1);
            // 如果昨天签到了,连续天数+1
            if (last.equals(yesterday)) {
                continuityDay = lastAttendance.getRewardDays() + 1;
            }
        }

        // 根据连续天数计算奖励积分
        int rewardPoints = 1;
        if (continuityDay <= RULES.size()) {
            rewardPoints = RULES.get(continuityDay - 1).getPoints();
        }

        // 保存签到记录
        Attendance attendance = new Attendance();
        attendance.setMemberId(memberId);
        attendance.setDate(today);
        attendance.setAttendancePoint(rewardPoints);
        attendance.setRewardDays(continuityDay);
        attendanceMapper.insert(attendance);

        // 增加会员积分
        memberMapper.incrementPoints(memberId, rewardPoints);

        // 记录积分流水
        PointsFlow flow = new PointsFlow();
        flow.setMemberId(memberId);
        flow.setChangeType(1);
        flow.setChangeNum(rewardPoints);
        flow.setReason("积分签到奖励");
        flow.setSourceType(5); // 签到
        pointsMapper.insert(flow);

        // 获取更新后的会员信息
        Member member = memberMapper.selectById(memberId);

        // 构建返回结果
        SignResultVO vo = new SignResultVO();
        vo.setPoints(rewardPoints);
        vo.setContinuousDay(continuityDay);
        vo.setTotalPoints(member != null ? member.getPointNum() : 0);

        log.info("签到: memberId={}, points={}, continuityDay={}", memberId, rewardPoints, continuityDay);
        return vo;
    }

    /**
     * 获取签到历史
     *
     * @param memberId 会员ID
     * @return 签到记录列表
     */
    @Override
    public List<Attendance> getSignHistory(Long memberId) {
        return attendanceMapper.selectHistoryByMemberId(memberId, 30);
    }
}
