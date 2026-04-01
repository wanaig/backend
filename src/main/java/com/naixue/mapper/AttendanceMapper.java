package com.naixue.mapper;

import com.naixue.entity.Attendance;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 签到记录Mapper接口
 *
 * 对应数据库表: attendance
 * 主要功能:
 * - 签到记录查询
 * - 签到记录新增
 * - 连续签到天数统计
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Mapper
public interface AttendanceMapper {

    /**
     * 查询会员指定日期的签到记录
     *
     * @param memberId 会员ID
     * @param date 签到日期 (格式: yyyy-MM-dd)
     * @return 签到记录,未找到返回null
     */
    @Select("SELECT * FROM attendance WHERE member_id = #{memberId} AND `date` = #{date} LIMIT 1")
    Attendance selectByMemberIdAndDate(@Param("memberId") Long memberId, @Param("date") String date);

    /**
     * 新增签到记录
     *
     * @param attendance 签到信息
     * @return 影响行数
     */
    @Insert("INSERT INTO attendance (member_id, `date`, attendance_point, reward_days, created_at) " +
            "VALUES (#{memberId}, #{date}, #{attendancePoint}, #{rewardDays}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Attendance attendance);

    /**
     * 查询会员最近7天的签到记录
     *
     * @param memberId 会员ID
     * @return 签到记录列表
     */
    @Select("SELECT * FROM attendance WHERE member_id = #{memberId} AND `date` >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) ORDER BY `date` DESC")
    List<Attendance> selectRecentByMemberId(@Param("memberId") Long memberId);

    /**
     * 统计连续签到天数
     *
     * @param memberId 会员ID
     * @param days 天数
     * @return 连续签到天数
     */
    @Select("SELECT COUNT(*) FROM attendance WHERE member_id = #{memberId} AND `date` >= DATE_SUB(CURDATE(), INTERVAL #{days} DAY)")
    int countContinuityDays(@Param("memberId") Long memberId, @Param("days") Integer days);

    /**
     * 查询会员签到历史
     *
     * @param memberId 会员ID
     * @param limit 返回记录数
     * @return 签到记录列表
     */
    @Select("SELECT * FROM attendance WHERE member_id = #{memberId} ORDER BY `date` DESC LIMIT #{limit}")
    List<Attendance> selectHistoryByMemberId(@Param("memberId") Long memberId, @Param("limit") Integer limit);
}
