package com.naixue.mapper;

import com.naixue.entity.PointsFlow;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 积分流水Mapper接口
 *
 * 对应数据库表: points_flow
 * 主要功能:
 * - 积分变动记录查询
 * - 积分变动记录新增
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Mapper
public interface PointsMapper {

    /**
     * 新增积分流水记录
     *
     * @param pointsFlow 积分流水信息
     * @return 影响行数
     */
    @Insert("INSERT INTO points_flow (member_id, change_type, change_num, reason, source_type, created_at) " +
            "VALUES (#{memberId}, #{changeType}, #{changeNum}, #{reason}, #{sourceType}, NOW())")
    int insert(PointsFlow pointsFlow);

    /**
     * 查询会员的所有积分流水
     *
     * 按创建时间降序排列
     *
     * @param memberId 会员ID
     * @return 流水列表
     */
    @Select("SELECT * FROM points_flow WHERE member_id = #{memberId} ORDER BY created_at DESC")
    List<PointsFlow> selectByMemberId(@Param("memberId") Long memberId);

    /**
     * 分页查询会员积分流水
     *
     * @param memberId 会员ID
     * @param offset 偏移量
     * @param limit 每页数量
     * @return 流水列表
     */
    @Select("SELECT * FROM points_flow WHERE member_id = #{memberId} ORDER BY created_at DESC LIMIT #{offset}, #{limit}")
    List<PointsFlow> selectByMemberIdWithPage(@Param("memberId") Long memberId,
                                              @Param("offset") Integer offset,
                                              @Param("limit") Integer limit);
}
